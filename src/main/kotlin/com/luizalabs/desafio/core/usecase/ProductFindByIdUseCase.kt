package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.component.UseCase
import com.luizalabs.desafio.core.gateway.ProductFindByIdGateway
import com.luizalabs.desafio.core.interactor.ProductFindByIdInteractor
import java.util.UUID

@UseCase
internal class ProductFindByIdUseCase(
    private val productFindByIdGateway: ProductFindByIdGateway
) : ProductFindByIdInteractor {
    override fun execute(id: UUID) = this.productFindByIdGateway.findById(id = id)
}
