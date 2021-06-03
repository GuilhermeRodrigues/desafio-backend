package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.entity.Customer

interface CustomerFindByEmailGateway {
    fun findByEmail(email: String): Customer?
}
