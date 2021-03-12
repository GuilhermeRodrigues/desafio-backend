package com.luizalabs.desafio.util

import com.luizalabs.desafio.config.getObjectMapper
import kotlin.reflect.KClass

private fun <T> readValue(value: String, clazz: Class<T>): T {
    val mapper = getObjectMapper()
    return mapper.readValue(value, clazz)
}

fun <T : Any> String.toObject(clazz: KClass<T>): T = readValue(this, clazz.java)
fun <T : Any> String.toObject(clazz: Class<T>): T = readValue(this, clazz)
