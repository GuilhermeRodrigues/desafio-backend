package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.DataProvider
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import com.luizalabs.desafio.core.gateway.CustomerFindByEmailGateway
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.mapper.toCore
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.CustomerRepository
import java.util.UUID

@DataProvider
class CustomerDataProvider(
    private val repository: CustomerRepository
) : CustomerFindByIdGateway,
    CustomerFindByEmailGateway,
    CustomerSaveGateway {

    override fun findById(id: UUID): Customer {
        return this.repository
            .findById(id)
            .orElseThrow { throw CustomerNotFoundException() }
            .toCore()
    }

    override fun findByEmail(email: String): Customer? {
        return this.repository
            .findByEmail(email)
            ?.toCore()
    }

    override fun save(customer: Customer): Customer {
        return this.repository
            .save(customer.toTable())
            .toCore()
    }
}
