package com.testapp.imagefinder

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform