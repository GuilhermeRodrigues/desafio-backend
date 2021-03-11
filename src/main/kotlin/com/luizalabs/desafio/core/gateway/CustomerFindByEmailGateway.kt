package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.Customer

interface CustomerFindByEmailGateway {
    fun findByEmail(email: String): Customer?
}
