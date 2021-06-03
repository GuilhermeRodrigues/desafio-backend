package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.component.UseCase
import com.luizalabs.desafio.core.domain.dto.CustomerUpdateDto
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.exception.EmailAlreadyUsedException
import com.luizalabs.desafio.core.gateway.CustomerFindByEmailGateway
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.core.interactor.CustomerUpdateInteractor
import java.util.UUID

@UseCase
internal class CustomerUpdateUseCase(
    private val customerSaveGateway: CustomerSaveGateway,
    private val customerFindByEmailGateway: CustomerFindByEmailGateway,
    private val customerFindByIdGateway: CustomerFindByIdGateway
) : CustomerUpdateInteractor {
    override fun execute(id: UUID, customerUpdateDto: CustomerUpdateDto): Customer {
        val customer = this.customerFindByIdGateway.findById(id = id)

        val emailAlreadyUsed = this.customerFindByEmailGateway.findByEmail(email = customerUpdateDto.email ?: "") != null

        if (customerUpdateDto.email != null && emailAlreadyUsed) {
            throw EmailAlreadyUsedException()
        }

        val newCustomer = customer.copy(
            name = customerUpdateDto.name ?: customer.name,
            email = customerUpdateDto.email ?: customer.email
        )

        return this.customerSaveGateway.save(newCustomer)
    }
}
