package com.luizalabs.desafio.config.http

import com.luizalabs.desafio.util.toObject
import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

data class HttpClientResponse(
    val statusCode: HttpStatus,
    val body: String?,
)

fun <T : Any> HttpClientResponse.getBody(clazz: KClass<T>): T? {
    if (body != null) {
        return body.toObject(clazz)
    }
    return null
}
