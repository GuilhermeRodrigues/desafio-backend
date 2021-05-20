package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.component.UseCase
import com.luizalabs.desafio.core.gateway.CustomerFindAllGateway
import com.luizalabs.desafio.core.interactor.CustomerFindAllInteractor
import org.springframework.data.domain.Pageable

@UseCase
internal class CustomerFindAllUseCase(
    private val customerFindAllGateway: CustomerFindAllGateway
) : CustomerFindAllInteractor {
    override fun execute(page: Pageable) = this.customerFindAllGateway.findAll(page = page)
}
