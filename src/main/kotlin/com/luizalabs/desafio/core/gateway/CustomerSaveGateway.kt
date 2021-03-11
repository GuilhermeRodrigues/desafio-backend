package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.Customer

interface CustomerSaveGateway {
    fun save(customer: Customer): Customer
}