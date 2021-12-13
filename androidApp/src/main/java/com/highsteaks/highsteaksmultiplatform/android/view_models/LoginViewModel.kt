package com.highsteaks.highsteaksmultiplatform.android.view_models

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.highsteaks.highsteaksmultiplatform.network.ApiService
import com.highsteaks.highsteaksmultiplatform.utils.KMMStorage
import com.highsteaks.highsteaksmultiplatform.utils.SPref
import com.highsteaks.highsteaksmultiplatform.utils.setString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class LoginViewModel: ViewModel() {
    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> = _loginState

    @ExperimentalSerializationApi
    fun authorize(username:String, password:String){
        viewModelScope.launch(Dispatchers.Main) {
            val response = ApiService.authorize(username = username, password = password)
            response?.let {
                //Todo: Shared preference is deprecated use DataStore
               // storage.putString("token", response.token)
                _loginState.value = response.token.isNotBlank()
            }
        }
    }
}