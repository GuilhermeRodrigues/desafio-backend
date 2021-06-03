package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.dto.CustomerCreateDto
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.exception.EmailAlreadyUsedException
import kotlin.jvm.Throws

interface CustomerCreateInteractor {
    @Throws(EmailAlreadyUsedException::class)
    fun execute(customerCreateDto: CustomerCreateDto): Customer
}
