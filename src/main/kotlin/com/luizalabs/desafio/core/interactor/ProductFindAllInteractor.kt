package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.provider.api.challenge.response.ProductResponse

interface ProductFindAllInteractor {
    fun execute(page: Int): ProductResponse
}
