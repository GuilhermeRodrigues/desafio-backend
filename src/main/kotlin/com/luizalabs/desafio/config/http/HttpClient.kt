package com.luizalabs.desafio.config.http

import org.springframework.http.HttpHeaders

interface HttpClient {
    fun get(path: String, headers: HttpHeaders? = null): HttpClientResponse
    fun put(path: String, body: Any? = null, headers: HttpHeaders? = null): HttpClientResponse
    fun patch(path: String, body: Any? = null, headers: HttpHeaders? = null): HttpClientResponse
    fun delete(path: String, headers: HttpHeaders? = null): HttpClientResponse
    fun post(path: String, body: Any? = null, headers: HttpHeaders? = null): HttpClientResponse
}
