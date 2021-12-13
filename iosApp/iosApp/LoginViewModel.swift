//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Sandro Kakhetelidze on 12.10.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
class LoginViewModel: ObservableObject {
    @Published var loginState: Bool = false
    
    private let repository: ApiService =  ApiService()
    private let pref:SPrefKt = SPrefKt(conte)
    
    func login(username:String, password: String){
        repository.authorize(username: username, password: password, completionHandler: { [self] response, error in
            if(error == nil){
                
                self.loginState = response?.token != ""
            }
        })
    }
}
