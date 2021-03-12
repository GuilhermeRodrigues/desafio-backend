package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.provider.api.challenge.exception.ProductInternalServerErrorException
import com.luizalabs.desafio.provider.api.challenge.response.ProductResponse
import kotlin.jvm.Throws

interface ProductFindAllGateway {
    @Throws(ProductInternalServerErrorException::class)
    fun findAll(page: Int): ProductResponse
}
