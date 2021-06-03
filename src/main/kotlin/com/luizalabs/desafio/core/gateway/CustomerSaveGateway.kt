package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.entity.Customer

interface CustomerSaveGateway {
    fun save(customer: Customer): Customer
}
