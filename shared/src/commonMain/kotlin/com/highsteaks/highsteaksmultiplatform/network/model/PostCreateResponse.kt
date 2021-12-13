package com.highsteaks.highsteaksmultiplatform.network.model

import kotlinx.serialization.Serializable

@Serializable
class PostCreateResponse(
    val OK: String,
    val posted : Boolean
)