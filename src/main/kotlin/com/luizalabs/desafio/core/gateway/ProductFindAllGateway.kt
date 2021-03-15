package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.provider.api.product.exception.ProductInternalServerErrorException
import com.luizalabs.desafio.provider.api.product.response.ProductResponse
import kotlin.jvm.Throws

interface ProductFindAllGateway {
    @Throws(ProductInternalServerErrorException::class)
    fun findAll(page: Int): ProductResponse
}
