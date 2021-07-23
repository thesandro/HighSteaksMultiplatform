//
//  CreatePostViewModel.swift
//  iosApp
//
//  Created by Sandro Kakhetelidze on 22.07.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class CreatePostViewModel: ObservableObject {
    private let repository: ApiService =  ApiService()

    func createPost(title:String,data: Data?){
        let byteArray = DataHelperKt.toByteArray(data!)
        repository.createPost(title: title, uploadFiles: byteArray, completionHandler: { data, _ in
            print("true" + (data ?? ""))
        })
    }
}

