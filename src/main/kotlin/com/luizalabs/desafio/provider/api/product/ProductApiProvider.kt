package com.luizalabs.desafio.provider.api.product

import com.luizalabs.desafio.annotation.ApiProvider
import com.luizalabs.desafio.config.http.HttpClient
import com.luizalabs.desafio.config.http.getBody
import com.luizalabs.desafio.core.exception.PageNotFoundException
import com.luizalabs.desafio.core.gateway.ProductFindAllGateway
import com.luizalabs.desafio.core.gateway.ProductFindByIdGateway
import com.luizalabs.desafio.provider.api.product.exception.ProductNotFoundException
import com.luizalabs.desafio.provider.api.product.response.Product
import com.luizalabs.desafio.provider.api.product.response.ProductResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import java.util.UUID

@ApiProvider
class ProductApiProvider(
    @Qualifier("productHttpClient")
    private val client: HttpClient
) : ProductFindAllGateway,
    ProductFindByIdGateway {
    override fun findAll(page: Int): ProductResponse {
        val response = this.client
            .get(
                "/product/?page=$page"
            )

        if (response.statusCode != HttpStatus.OK) {
            throw PageNotFoundException()
        }

        return response.getBody(ProductResponse::class)!!
    }

    override fun findById(id: UUID): Product {
        val response = this.client
            .get(
                "/product/$id/"
            )

        if (response.statusCode != HttpStatus.OK) {
            throw ProductNotFoundException()
        }

        return response.getBody(Product::class)!!
    }
}
