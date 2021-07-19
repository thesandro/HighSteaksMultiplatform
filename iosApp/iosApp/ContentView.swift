import SwiftUI
import shared

struct ContentView: View {
    let greet: Void = ApiService().getPosts(page: 0, completionHandler: {data,_ in
        for item in data?.result ?? [] {
            let post = item as! Post
            print(post.title)
        }
})
    
    var body: some View {
        Text("hey")
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}


//struct LoginView: View {
//    @State var login = ""
//    @State var password = ""
//    
//    var body: some View {
//        VStack(
//        ) {
//            TextField("login", text: $login)
//            TextField("password", text: $password)
//            Button(action: {}) {
//                Text("Enter")
//            }
//        }
//    }
//}
