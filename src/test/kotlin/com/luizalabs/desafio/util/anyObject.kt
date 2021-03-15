package com.luizalabs.desafio.util

import org.mockito.Mockito

private fun <T> uninitialized(): T = null as T

fun <T> anyObject(): T {
    Mockito.anyObject<T>()
    return uninitialized()
}

fun <T> anyObject(clazz: Class<T>): T {
    Mockito.any<T>(clazz)
    return uninitialized()
}
