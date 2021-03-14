package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.provider.api.challenge.response.Product
import java.util.UUID

interface ProductFindByIdInteractor {
    fun execute(id: UUID): Product
}
