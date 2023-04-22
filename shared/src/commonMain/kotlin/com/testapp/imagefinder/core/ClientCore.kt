package com.testapp.imagefinder.core

import io.ktor.client.features.*
import io.ktor.client.request.*

class ClientCore(
    private val clientInfo: ClientInfo,
    private val configuration: Configuration
) {

    val apiKey = configuration.apiKey
    val client = clientInfo.getClient()

    init {
        client.config {
            expectSuccess = true
            defaultRequest {
                host = ApiLinks.BASE_URL
            }
        }
    }
}