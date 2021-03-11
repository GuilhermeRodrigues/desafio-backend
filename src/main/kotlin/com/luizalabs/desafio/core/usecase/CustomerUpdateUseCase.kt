package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UseCase
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.core.interactor.CustomerUpdateInteractor
import com.luizalabs.desafio.entrypoint.api.request.CustomerUpdateRequest
import java.util.UUID

@UseCase
internal class CustomerUpdateUseCase(
    private val customerSaveGateway: CustomerSaveGateway,
    private val customerFindByIdGateway: CustomerFindByIdGateway
) : CustomerUpdateInteractor {
    override fun execute(id: UUID, customerUpdateRequest: CustomerUpdateRequest): Customer {
        val customer = this.customerFindByIdGateway.findById(id = id)

        val newCustomer = customer.copy(
            name = customerUpdateRequest.name ?: customer.name,
            email = customerUpdateRequest.email ?: customer.email
        )

        return this.customerSaveGateway.save(newCustomer)
    }
}
