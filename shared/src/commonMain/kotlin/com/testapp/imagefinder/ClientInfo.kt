package com.testapp.imagefinder

import io.ktor.client.*

expect class ClientInfo() {
    fun getClient(): HttpClient

}