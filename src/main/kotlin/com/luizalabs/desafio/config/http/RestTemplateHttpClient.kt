package com.luizalabs.desafio.config.http

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.HttpStatusCodeException
import java.net.URI
import java.time.Duration

class RestTemplateHttpClient(
    private val restTemplateBuilder: RestTemplateBuilder,
    private val baseUrl: String,
    private val authorization: String? = null,
    private val timeout: Long = 3000,
    private val headers: HttpHeaders = HttpHeaders()
) : HttpClient {
    init {
        if (this.authorization != null) {
            this.headers.set("Authorization", this.authorization)
        }
    }
    override fun get(path: String, headers: HttpHeaders?): HttpClientResponse {
        return this.exchange(
            method = HttpMethod.GET,
            path = path,
            body = null,
            timeout = this.timeout,
            headers = headers
        )
    }

    override fun post(path: String, body: Any?, headers: HttpHeaders?): HttpClientResponse {
        return this.exchange(
            method = HttpMethod.POST,
            path = path,
            body = body,
            timeout = this.timeout,
            headers = headers
        )
    }

    override fun put(path: String, body: Any?, headers: HttpHeaders?): HttpClientResponse {
        return this.exchange(
            method = HttpMethod.PUT,
            path = path,
            body = body,
            timeout = this.timeout,
            headers = headers
        )
    }

    override fun patch(path: String, body: Any?, headers: HttpHeaders?): HttpClientResponse {
        return this.exchange(
            method = HttpMethod.PATCH,
            path = path,
            body = body,
            timeout = this.timeout,
            headers = headers
        )
    }

    override fun delete(path: String, headers: HttpHeaders?): HttpClientResponse {
        return this.exchange(
            method = HttpMethod.DELETE,
            path = path,
            body = null,
            timeout = this.timeout,
            headers = headers
        )
    }

    private fun exchange(
        method: HttpMethod,
        path: String,
        body: Any?,
        timeout: Long,
        headers: HttpHeaders? = null
    ): HttpClientResponse {
        try {
            val template = this.restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(timeout))
                .setReadTimeout(Duration.ofMillis(timeout))
                .build()

            val response = template.exchange(
                URI(this.baseUrl + path),
                method,
                this.getHttpEntity(body, headers),
                String::class.java
            )

            return HttpClientResponse(
                statusCode = response.statusCode,
                body = response.body,
            )
        } catch (t: HttpStatusCodeException) {
            return HttpClientResponse(
                statusCode = t.statusCode,
                body = t.responseBodyAsString
            )
        }
    }

    private fun getHttpEntity(body: Any? = null, headers: HttpHeaders? = null): HttpEntity<*> {
        val completeHeaders = HttpHeaders()
        this.headers.forEach {
            completeHeaders[it.key] = it.value
        }
        headers?.forEach {
            completeHeaders[it.key] = it.value
        }
        return HttpEntity(body, completeHeaders)
    }
}
