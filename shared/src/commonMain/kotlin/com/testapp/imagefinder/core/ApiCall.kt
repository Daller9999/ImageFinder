package com.testapp.imagefinder.core

import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

abstract class ApiCall(
    private val clientCore: ClientCore,
) {

    val client = clientCore.client

    protected fun HttpRequestBuilder.setBuilderWithKey(method: HttpMethod) {
        this.method = method
        parameter("key", clientCore.configuration.apiKey)
    }

    suspend fun <T> makeApiCall(call: suspend () -> T?): Pair<T?, Int?> {
        return try {
            val result = call.invoke()
            Pair(result, null)
        } catch (ex: ClientRequestException) {
            ex.printStackTrace()
            Pair(null, ex.response.status.value)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Pair(null, null)
        }
    }

}