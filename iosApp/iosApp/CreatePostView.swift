//
//  CreatePostView.swift
//  iosApp
//
//  Created by Sandro Kakhetelidze on 20.07.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

extension CreatePostView {
    final class ViewModel: ObservableObject {
        @Published var selectedImage: UIImage?
        @Published var isPresentingImagePicker = false
        private(set) var sourceType: ImagePicker.SourceType = .camera
        
        func choosePhoto() {
            sourceType = .photoLibrary
            isPresentingImagePicker = true
        }
        
        func takePhoto() {
            sourceType = .camera
            isPresentingImagePicker = true
        }
        
        func didSelectImage(_ image: UIImage?) {
            selectedImage = image
            isPresentingImagePicker = false
        }
    }
}

struct CreatePostView: View {
    @State var title = ""
    @State var description = ""
    @State var tags = ""
    @ObservedObject var viewModel = ViewModel()
    @ObservedObject var createPostViewModel = CreatePostViewModel()
    var body: some View {
        VStack(alignment: .leading) {
            TextField("Title", text: $title).padding(32)
            TextField("Description", text: $description).padding(32)
            TextField("Tags", text: $tags).padding(32)
            Button(action:{
                viewModel.choosePhoto()
            }){
                imageView(for: viewModel.selectedImage)
            }
            .padding(32)
            .fullScreenCover(isPresented: $viewModel.isPresentingImagePicker, content: {
                ImagePicker(sourceType: viewModel.sourceType, completionHandler: viewModel.didSelectImage)
            })
            Button(action: {
                let data = viewModel.selectedImage?.jpegData(compressionQuality: 1.0)
                createPostViewModel.createPost(title: title, data: data)
            }){
                Text("Create Post")
            }
        }
    }
}

struct CreatePostView_Previews: PreviewProvider {
    static var previews: some View {
        CreatePostView()
    }
}

@ViewBuilder
    func imageView(for image: UIImage?) -> some View {
        if let image = image {
            Image(uiImage: image)
                .resizable()
                .frame(width: 100, height: 100, alignment: .leading)
        }
        else {
            Image("plus")
        }
    }

struct ImagePicker: UIViewControllerRepresentable {
    typealias UIViewControllerType = UIImagePickerController
    typealias SourceType = UIImagePickerController.SourceType

    let sourceType: SourceType
    let completionHandler: (UIImage?) -> Void
    
    func makeUIViewController(context: Context) -> UIImagePickerController {
        let viewController = UIImagePickerController()
        viewController.delegate = context.coordinator
        viewController.sourceType = sourceType
        return viewController
    }
    
    func updateUIViewController(_ uiViewController: UIImagePickerController, context: Context) {}
    
    func makeCoordinator() -> Coordinator {
        return Coordinator(completionHandler: completionHandler)
    }
    
    final class Coordinator: NSObject, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
        let completionHandler: (UIImage?) -> Void
        
        init(completionHandler: @escaping (UIImage?) -> Void) {
            self.completionHandler = completionHandler
        }
        
        func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey: Any]) {
            let image: UIImage? = {
                if let image = info[.editedImage] as? UIImage {
                    return image
                }
                return info[.originalImage] as? UIImage
            }()
            completionHandler(image)
        }

        func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
            completionHandler(nil)
        }
    }
}
