package com.highsteaks.highsteaksmultiplatform.network.model

import kotlinx.serialization.Serializable

@Serializable
class ErrorResponse(
    val OK: Boolean,
    val error: String
)