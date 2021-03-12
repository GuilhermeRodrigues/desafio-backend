package com.luizalabs.desafio.config.http

import com.luizalabs.desafio.config.getObjectMapper
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

fun <T : Any> HttpClientResponse.getRecords(clazz: KClass<Array<T>>): List<T> {
    val mapper = getObjectMapper()
    if (body != null) {
        val jsonNode = mapper.readTree(body)
        return mapper
            .convertValue(jsonNode.get("records"), clazz.java)
            .toList()
    }
    return listOf()
}

fun <T : Any> HttpClientResponse.getRecord(clazz: KClass<T>): T? {
    val mapper = getObjectMapper()
    if (body != null) {
        val jsonNode = mapper.readTree(body)
        val items = jsonNode.get("records")
        if (!items.isEmpty) {
            return mapper.convertValue(items.get(0), clazz.java)
        }
    }
    return null
}

fun HttpClientResponse.isOk(): Boolean = statusCode.is2xxSuccessful && !statusCode.isError
fun HttpClientResponse.isNotOk(): Boolean = !this.isOk()
