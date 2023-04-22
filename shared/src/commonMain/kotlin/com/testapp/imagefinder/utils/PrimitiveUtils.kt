package com.testapp.imagefinder.utils

fun Int?.orNull(): Int = this ?: -1
fun String?.orNull(): String = this ?: ""
fun <T> List<T>?.orNull(): List<T> = this ?: emptyList()