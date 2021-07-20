package com.highsteaks.highsteaksmultiplatform.network.model

import kotlinx.serialization.Serializable

@Serializable
class PostListResponse(
    val result:List<Post>,
    val nextPage:Int
)