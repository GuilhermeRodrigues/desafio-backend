package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.provider.api.product.response.ProductResponse

interface ProductFindAllInteractor {
    fun execute(page: Int): ProductResponse
}
