package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UseCase
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.interactor.CustomerFindByIdInteractor
import java.util.UUID

@UseCase
internal class CustomerFindByIdUseCase(
    private val customerFindByIdGateway: CustomerFindByIdGateway
) : CustomerFindByIdInteractor {
    override fun execute(id: UUID) = this.customerFindByIdGateway.findById(id = id)
}
