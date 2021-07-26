//
//  HomeView.swift
//  iosApp
//
//  Created by Sandro Kakhetelidze on 20.07.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import SDWebImageSwiftUI

struct HomeView: View {
    @ObservedObject var homeViewModel = HomeViewModel()
    @State var selected = 0
    
    @State var indicator = true
    var body: some View {
        VStack(alignment: .leading){
            ZStack(alignment: .top){
                BottomBar(selected: self.$selected)
                    .padding()
                    .padding(.horizontal, 22)
                    .frame(maxWidth: .infinity)
            }.background(Color.gray.edgesIgnoringSafeArea(.all))
            HStack {
                Image("bonkybear").resizable().aspectRatio(contentMode: .fill)
                    .frame(width: 40, height: 40).clipShape(Circle())
                NavigationLink(destination: CreatePostView()
                                .navigationBarTitle("Create Post", displayMode: .inline)) {
                    Text("Do you want to make a post?")
                        .foregroundColor(Color.gray)
                }.navigationBarTitle("", displayMode: .inline)
                .frame(
                    minWidth: 0,
                    maxWidth: .infinity,
                    minHeight: 0,
                    maxHeight: 30,
                    alignment: .leading
                )
                .overlay(
                    RoundedRectangle(cornerRadius: 20)
                        .stroke(Color.gray, lineWidth: 2).padding(.leading,-8).padding(.trailing,8))
                .padding(.leading,5)
            }.padding(.leading,5)
            
            List(homeViewModel.postList, id: \.id){ post in
                VStack(alignment: .center) {
                    PostView(post: post)
                    if(homeViewModel.postList.firstIndex(of: post) == homeViewModel.postList.count-1 && !homeViewModel.lastPage){
                        ProgressView().onAppear {
                            homeViewModel.fetchPosts()
                        }
                    }
                }
            }
            
        }
    }
    
    struct HomeView_Previews: PreviewProvider {
        static var previews: some View {
            HomeView()
        }
    }
    
    struct PostView : View {
        var post:Post
        var body: some View {
            VStack(alignment: .leading) {
                HStack() {
                    Image("bonkybear").resizable().aspectRatio(contentMode: .fill)
                        .frame(width: 50, height: 50).clipShape(Circle())
                    Text(post.full_name)
                }
                Text(post.description_)
                WebImage(url: URL(string: post.urls[0].url)).resizable().scaledToFit()
            }
        }
    }
    
    struct BottomBar : View {
        
        @Binding var selected : Int
        
        var body : some View{
            
            HStack(){
                Button(action: {
                    
                    self.selected = 0
                    
                }) {
                    
                    Image("home")
                    
                }.foregroundColor(self.selected == 0 ? .black : .gray)
                Spacer()
                Button(action: {
                    
                    self.selected = 1
                    
                }) {
                    
                    Image("home")
                    
                }.foregroundColor(self.selected == 1 ? .black : .gray)
                Spacer()
                Button(action: {
                    
                    self.selected = 2
                    
                }) {
                    
                    Image("home")
                    
                }.foregroundColor(self.selected == 2 ? .black : .gray)
                Spacer()
                Button(action: {
                    
                    self.selected = 3
                    
                }) {
                    
                    Image("home")
                    
                }.foregroundColor(self.selected == 3 ? .black : .gray)
            }
        }
    }
}
