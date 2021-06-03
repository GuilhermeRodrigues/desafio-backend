package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.component.UseCase
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.core.interactor.CustomerDeleteInteractor
import java.time.LocalDateTime
import java.util.UUID

@UseCase
internal class CustomerDeleteUseCase(
    private val customerFindByIdGateway: CustomerFindByIdGateway,
    private val customerSaveGateway: CustomerSaveGateway
) : CustomerDeleteInteractor {
    override fun execute(id: UUID): Customer {
        val customer = this.customerFindByIdGateway.findById(id = id)

        val newCustomer = customer.copy(
            deletedAt = LocalDateTime.now()
        )

        return this.customerSaveGateway.save(newCustomer)
    }
}
