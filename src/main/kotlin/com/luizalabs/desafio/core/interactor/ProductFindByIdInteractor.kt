package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.provider.api.product.response.Product
import java.util.UUID

interface ProductFindByIdInteractor {
    fun execute(id: UUID): Product
}
