package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.entrypoint.api.request.CustomerRequest

interface CustomerSaveInteractor {
    fun execute(customerRequest: CustomerRequest): Customer
}
