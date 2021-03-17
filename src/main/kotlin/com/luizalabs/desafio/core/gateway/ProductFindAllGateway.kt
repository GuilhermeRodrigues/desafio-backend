package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.exception.PageNotFoundException
import com.luizalabs.desafio.provider.api.product.response.ProductResponse
import kotlin.jvm.Throws

interface ProductFindAllGateway {
    @Throws(PageNotFoundException::class)
    fun findAll(page: Int): ProductResponse
}
