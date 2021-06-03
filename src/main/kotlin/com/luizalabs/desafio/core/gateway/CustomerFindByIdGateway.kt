package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import java.util.UUID
import kotlin.jvm.Throws

interface CustomerFindByIdGateway {
    @Throws(CustomerNotFoundException::class)
    fun findById(id: UUID): Customer
}
