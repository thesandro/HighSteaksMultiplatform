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
    var currentPage:Int32 = 0
    var lastPage = false
    
    private let repository: ApiService =  ApiService()
    init(){
        fetchPosts()
    }
    
    func fetchPosts() {
        repository.getPosts(page: currentPage, completionHandler: {   data, _ in
            if(data !== nil){
                if(data?.result.count != 0){
                    self.currentPage = data!.nextPage
                }
                else{
                    self.lastPage = true
                }
                
                self.postList.append(contentsOf: data!.result)
            }
        })
    }
    
    
}

