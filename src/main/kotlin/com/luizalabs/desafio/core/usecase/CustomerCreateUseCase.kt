package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.component.UseCase
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.exception.EmailAlreadyUsedException
import com.luizalabs.desafio.core.gateway.CustomerFindByEmailGateway
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.core.interactor.CustomerCreateInteractor
import com.luizalabs.desafio.entrypoint.api.request.CustomerCreateRequest
import com.luizalabs.desafio.mapper.toCore

@UseCase
internal class CustomerCreateUseCase(
    private val customerSaveGateway: CustomerSaveGateway,
    private val customerFindByEmailGateway: CustomerFindByEmailGateway
) : CustomerCreateInteractor {
    override fun execute(customerCreateRequest: CustomerCreateRequest): Customer {
        val customer = this.customerFindByEmailGateway.findByEmail(email = customerCreateRequest.email)

        if (customer != null) {
            throw EmailAlreadyUsedException()
        }

        return this.customerSaveGateway.save(customerCreateRequest.toCore())
    }
}
