package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.entrypoint.api.request.CustomerCreateRequest

interface CustomerCreateInteractor {
    fun execute(customerCreateRequest: CustomerCreateRequest): Customer
}
