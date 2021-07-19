package com.highsteaks.highsteaksmultiplatform.android.view_models

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.highsteaks.highsteaksmultiplatform.network.model.Post
import kotlinx.coroutines.flow.Flow

class PostsViewModel : ViewModel() {
    val posts: Flow<PagingData<Post>> = Pager(PagingConfig(pageSize = 5)) {
        MovieSource()
    }.flow
}