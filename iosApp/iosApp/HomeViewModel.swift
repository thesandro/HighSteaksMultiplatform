//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Sandro Kakhetelidze on 20.07.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class HomeViewModel: ObservableObject {
    @Published var postList = [Post]()
    
    private let repository: ApiService =  ApiService()
    init(){
        fetchPosts()
    }
    func fetchPosts() {
        repository.getPosts(page: 0, completionHandler: {   data, _ in
            if(data !== nil){
                self.postList = data!.result
            }
        })
    }
}

