package com.highsteaks.highsteaksmultiplatform.network

import com.highsteaks.highsteaksmultiplatform.network.model.PostListResponse
import io.ktor.client.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


object ApiService {
    private const val BASE_PATH = "https://ktorhighsteaks.herokuapp.com"
    private val ktorClient = HttpClient() {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
    }
    private val jsonBuilder = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    suspend fun getPosts(page: Int): PostListResponse {
        val response = ktorClient.get<HttpResponse>("$BASE_PATH/posts?page=${page}")
        val stringResponse = response.readText()
        return jsonBuilder.decodeFromString(stringResponse)
    }

    suspend fun createPost(
        title:String,
        uploadFiles: ByteArray,
    ): String {

        val data = mapOf(
            "user_id" to 1,
            "title" to title,
            "description" to "multi platform storng",
            "category_id" to 1,
            "tags" to "le meem",
            "price" to 500,
            "price_type" to "GELA"
        )
        val files = mapOf(
            "file" to uploadFiles
        )

        val response =
            ktorClient.post<HttpResponse>("https://ktorhighsteaks.herokuapp.com/create-post") {
                headers {
                    header(
                        HttpHeaders.Authorization,
                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE2MjgzMzQzNDF9.eQIccFu0o2K2Tp_qOcQny0UPTamTDmt6nuyfsADivQs"
                    )
                }
                body = MultiPartFormDataContent(
                    formData {
                        data.forEach {
                            append(FormPart(it.key, it.value))
                        }
                        files.forEach {
                            append(it.key, it.value, Headers.build {
                                append(HttpHeaders.ContentType, "images/*") // Mime type required
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "filename=formImage"
                                ) // Filename in content disposition required
                            })
                        }
                    }
                )
            }
        return response.readText()
    }
}