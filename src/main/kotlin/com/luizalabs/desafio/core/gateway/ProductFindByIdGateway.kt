package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.provider.api.challenge.exception.ProductNotFoundException
import com.luizalabs.desafio.provider.api.challenge.response.Product
import java.util.UUID
import kotlin.jvm.Throws

interface ProductFindByIdGateway {
    @Throws(ProductNotFoundException::class)
    fun findById(id: UUID): Product
}
