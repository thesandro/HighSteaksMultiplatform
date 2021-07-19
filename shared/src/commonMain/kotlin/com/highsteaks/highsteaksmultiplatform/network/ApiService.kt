package com.highsteaks.highsteaksmultiplatform.android.network

import com.highsteaks.highsteaksmultiplatform.network.model.PostListResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


object ApiService {
    private const val BASE_PATH = "https://ktorhighsteaks.herokuapp.com/"
    private val ktorClient =  HttpClient()
    private val jsonBuilder = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    suspend fun getPosts(page:Int): PostListResponse {
        val response = ktorClient.get<HttpResponse>( "$BASE_PATH/posts?page=${page}")
        val stringResponse = response.readText()
        return jsonBuilder.decodeFromString(stringResponse)
    }
}