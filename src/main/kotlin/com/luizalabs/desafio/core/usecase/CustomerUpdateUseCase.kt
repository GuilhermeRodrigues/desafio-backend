package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.component.UseCase
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.exception.EmailAlreadyUsedException
import com.luizalabs.desafio.core.gateway.CustomerFindByEmailGateway
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.core.interactor.CustomerUpdateInteractor
import com.luizalabs.desafio.entrypoint.api.request.CustomerUpdateRequest
import java.util.UUID

@UseCase
internal class CustomerUpdateUseCase(
    private val customerSaveGateway: CustomerSaveGateway,
    private val customerFindByEmailGateway: CustomerFindByEmailGateway,
    private val customerFindByIdGateway: CustomerFindByIdGateway
) : CustomerUpdateInteractor {
    override fun execute(id: UUID, customerUpdateRequest: CustomerUpdateRequest): Customer {
        val customer = this.customerFindByIdGateway.findById(id = id)

        val emailAlreadyUsed = this.customerFindByEmailGateway.findByEmail(email = customerUpdateRequest.email ?: "") != null

        if (customerUpdateRequest.email != null && emailAlreadyUsed) {
            throw EmailAlreadyUsedException()
        }

        val newCustomer = customer.copy(
            name = customerUpdateRequest.name ?: customer.name,
            email = customerUpdateRequest.email ?: customer.email
        )

        return this.customerSaveGateway.save(newCustomer)
    }
}
