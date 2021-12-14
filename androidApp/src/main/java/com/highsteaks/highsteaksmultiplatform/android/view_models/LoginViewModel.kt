package com.highsteaks.highsteaksmultiplatform.android.view_models


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.highsteaks.highsteaksmultiplatform.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> = _loginState

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