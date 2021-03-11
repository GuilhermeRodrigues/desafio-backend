package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.DataProvider
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.mapper.toCore
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.CustomerRepository

@DataProvider
class CustomerDataProvider(
    private val repository: CustomerRepository
) : CustomerSaveGateway {
    override fun save(customer: Customer): Customer {
        return this.repository
            .save(customer.toTable())
            .toCore()
    }
}
