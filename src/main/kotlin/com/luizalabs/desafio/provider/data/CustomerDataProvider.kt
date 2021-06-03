package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.component.DataProvider
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import com.luizalabs.desafio.core.exception.PageNotFoundException
import com.luizalabs.desafio.core.gateway.CustomerFindAllGateway
import com.luizalabs.desafio.core.gateway.CustomerFindByEmailGateway
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.mapper.toCore
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

@DataProvider
class CustomerDataProvider(
    private val repository: CustomerRepository
) : CustomerFindByIdGateway,
    CustomerFindByEmailGateway,
    CustomerFindAllGateway,
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

    override fun findAll(page: Pageable): Page<Customer> {
        return this.repository
            .findAll(page)
            .map { it.toCore() }
            .apply {
                if (this.isEmpty) {
                    throw PageNotFoundException()
                }
            }
    }

    override fun save(customer: Customer): Customer {
        return this.repository
            .save(customer.toTable())
            .toCore()
    }
}
