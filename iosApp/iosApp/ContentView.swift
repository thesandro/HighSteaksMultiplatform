import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        LoginView()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}


struct LoginView: View {
    @State var login = ""
    @State var password = ""
    
    @State var selection: Int? = nil
    
    var body: some View {
        
        NavigationView{
            VStack(
            ) {
                Image("bonkybear")
                    .frame(200,200)
                TextField("Username", text: $login).padding(32)
                TextField("Password", text: $password).padding(32)
                NavigationLink(destination: HomeView(), tag:1, selection: $selection) {
                    Text("Enter").padding(8).frame(width: 200, height: 48, alignment: .center)
                }
            }
        }
    }
}
