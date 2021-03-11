package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UseCase
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.core.interactor.CustomerSaveInteractor
import com.luizalabs.desafio.entrypoint.api.request.CustomerRequest
import com.luizalabs.desafio.mapper.toCore

@UseCase
internal class CustomerSaveUseCase(
    private val customerSaveGateway: CustomerSaveGateway
) : CustomerSaveInteractor {
    override fun execute(customerRequest: CustomerRequest): Customer {
        return this.customerSaveGateway.save(customerRequest.toCore())
    }
}
