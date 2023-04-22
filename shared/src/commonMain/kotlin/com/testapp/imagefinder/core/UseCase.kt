package com.testapp.imagefinder.core

import io.ktor.client.request.*
import io.ktor.http.*

abstract class UseCase(
    private val clientCore: ClientCore
) {

    val client = clientCore.client

    protected fun HttpRequestBuilder.setBuilderWithKey(method: HttpMethod) {
        this.method = method
        parameter("key", clientCore.apiKey)
    }

}