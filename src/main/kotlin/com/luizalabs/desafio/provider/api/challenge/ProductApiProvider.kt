package com.luizalabs.desafio.provider.api.challenge

import com.luizalabs.desafio.annotation.ApiProvider
import com.luizalabs.desafio.config.http.HttpClient
import com.luizalabs.desafio.config.http.getBody
import com.luizalabs.desafio.core.gateway.ProductFindAllGateway
import com.luizalabs.desafio.provider.api.challenge.exception.ProductInternalServerErrorException
import com.luizalabs.desafio.provider.api.challenge.response.ProductResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus

@ApiProvider
class ProductApiProvider(
    @Qualifier("productHttpClient")
    private val client: HttpClient
) : ProductFindAllGateway {
    override fun findAll(page: Int): ProductResponse {
        val response = this.client
            .get(
                "/product/?page=$page"
            )

        if (response.statusCode != HttpStatus.OK) {
            throw ProductInternalServerErrorException()
        }

        return response.getBody(ProductResponse::class)!!
    }
}
