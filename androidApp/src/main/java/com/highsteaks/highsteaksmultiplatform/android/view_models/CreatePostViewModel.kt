package com.highsteaks.highsteaksmultiplatform.android.view_models

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.highsteaks.highsteaksmultiplatform.network.ApiService
import com.highsteaks.highsteaksmultiplatform.network.model.PostListResponse
import io.ktor.client.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CreatePostViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(arrayListOf<Uri>())
    val uiState: StateFlow<ArrayList<Uri>> = _uiState

    fun addPhoto(uri: Uri){
        val uris = arrayListOf(uri)
        uris.addAll(uiState.value)
        _uiState.value = uris
    }


    fun createPost(title:String, byteArray: ByteArray) {
        viewModelScope.launch(Dispatchers.Default){
            val result = ApiService.createPost(title = title, uploadFiles = byteArray)

        }
    }
}