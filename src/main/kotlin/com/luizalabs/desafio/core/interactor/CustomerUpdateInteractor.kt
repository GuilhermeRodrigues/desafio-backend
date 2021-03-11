package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.entrypoint.api.request.CustomerUpdateRequest
import java.util.UUID

interface CustomerUpdateInteractor {
    fun execute(id: UUID, customerUpdateRequest: CustomerUpdateRequest): Customer
}
