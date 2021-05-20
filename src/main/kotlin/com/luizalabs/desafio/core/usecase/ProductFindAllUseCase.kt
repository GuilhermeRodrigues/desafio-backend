package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.component.UseCase
import com.luizalabs.desafio.core.gateway.ProductFindAllGateway
import com.luizalabs.desafio.core.interactor.ProductFindAllInteractor

@UseCase
internal class ProductFindAllUseCase(
    private val productFindAllGateway: ProductFindAllGateway
) : ProductFindAllInteractor {
    override fun execute(page: Int) = this.productFindAllGateway.findAll(page = page)
}
