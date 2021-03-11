package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.exception.EmailAlreadyUsedException
import com.luizalabs.desafio.entrypoint.api.request.CustomerCreateRequest
import kotlin.jvm.Throws

interface CustomerCreateInteractor {
    @Throws(EmailAlreadyUsedException::class)
    fun execute(customerCreateRequest: CustomerCreateRequest): Customer
}
