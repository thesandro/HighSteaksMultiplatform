package com.highsteaks.highsteaksmultiplatform.android.view_models

import android.util.Log.d
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.highsteaks.highsteaksmultiplatform.network.ApiService
import com.highsteaks.highsteaksmultiplatform.network.model.Post

class MovieSource(
) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {

            d("PagingSourceParams","params: ${params.key}")
            val nextPage = params.key ?: 0
            val postListResponse = ApiService.getPosts(nextPage)

            LoadResult.Page(
                data = postListResponse!!.result,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if(postListResponse.result.isNotEmpty())postListResponse.nextPage else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}