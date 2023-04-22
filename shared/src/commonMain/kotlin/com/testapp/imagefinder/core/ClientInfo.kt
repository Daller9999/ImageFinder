package com.testapp.imagefinder.core

import io.ktor.client.*

expect class ClientInfo() {
    fun getClient(): HttpClient

}