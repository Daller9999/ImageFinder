package com.testapp.imagefinder.core

import io.ktor.client.features.*
import io.ktor.client.request.*

class ClientCore(
    clientInfo: ClientInfo,
    val configuration: Configuration
) {

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