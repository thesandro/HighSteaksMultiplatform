package com.highsteaks.highsteaksmultiplatform.android.view_models

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreatePostViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(arrayListOf<Uri>())
    val uiState: StateFlow<ArrayList<Uri>> = _uiState

    fun addPhoto(uri: Uri){
        val uris = arrayListOf(uri)
        uris.addAll(uiState.value)
        _uiState.value = uris
    }
}