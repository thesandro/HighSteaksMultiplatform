//
//  HomeView.swift
//  iosApp
//
//  Created by Sandro Kakhetelidze on 20.07.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HomeView: View {
    @ObservedObject var cityBikesViewModel = HomeViewModel()
    
    var body: some View {
        List(cityBikesViewModel.postList, id: \.id){ post in
            PostView(post: post)
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
                Image("bonkybear").resizable()
                    .frame(width: 50, height: 50)
                Text(post.full_name)
            }
            Text(post.description_)
            Image("bonkybear").resizable().frame(width: 250, height: 250,alignment: .center)
                .aspectRatio(contentMode: .fill)
        }
    }
}

