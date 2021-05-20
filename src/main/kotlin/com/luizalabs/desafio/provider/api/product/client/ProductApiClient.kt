package com.luizalabs.desafio.provider.api.product.client

import com.luizalabs.desafio.annotation.component.ApiClient
import com.luizalabs.desafio.config.http.HttpClient
import com.luizalabs.desafio.config.http.RestTemplateHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean

@ApiClient
class ProductApiClient(
    @Value("\${application.client.product.url}")
    private val baseUrl: String,
    @Value("\${application.client.product.timeout}")
    private val timeout: Long
) {
    @Bean(name = ["productHttpClient"])
    fun client(restTemplateBuilder: RestTemplateBuilder): HttpClient {
        return RestTemplateHttpClient(
            baseUrl = baseUrl,
            restTemplateBuilder = restTemplateBuilder,
            timeout = timeout
        )
    }
}
