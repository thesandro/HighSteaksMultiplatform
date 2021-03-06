package com.highsteaks.highsteaksmultiplatform.android.view_models

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.highsteaks.highsteaksmultiplatform.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreatePostViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(arrayListOf<Uri>())
    val uiState: StateFlow<ArrayList<Uri>> = _uiState

    private val _postCreationState = MutableStateFlow(false)
    val postCreationState:StateFlow<Boolean> = _postCreationState

    fun addPhoto(uri: Uri){
        val uris = arrayListOf(uri)
        uris.addAll(uiState.value)
        _uiState.value = uris
    }


    fun createPost(title:String, byteArray: ByteArray) {
        viewModelScope.launch(Dispatchers.Default){
            val result = ApiService.createPost(title = title, uploadFiles = byteArray)
            result?.let {
                _postCreationState.value = result.posted
            }

        }
    }
}