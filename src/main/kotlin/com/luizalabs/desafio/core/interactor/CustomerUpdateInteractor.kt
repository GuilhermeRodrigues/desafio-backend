package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.exception.EmailAlreadyUsedException
import com.luizalabs.desafio.entrypoint.api.request.CustomerUpdateRequest
import java.util.UUID
import kotlin.jvm.Throws

interface CustomerUpdateInteractor {
    @Throws(EmailAlreadyUsedException::class)
    fun execute(id: UUID, customerUpdateRequest: CustomerUpdateRequest): Customer
}
