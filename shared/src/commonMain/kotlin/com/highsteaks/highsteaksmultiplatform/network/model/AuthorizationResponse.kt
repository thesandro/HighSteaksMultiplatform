package com.highsteaks.highsteaksmultiplatform.network.model

import kotlinx.serialization.Serializable

@Serializable
class AuthorizationResponse(
    val user_id:Int,
    val token: String
)