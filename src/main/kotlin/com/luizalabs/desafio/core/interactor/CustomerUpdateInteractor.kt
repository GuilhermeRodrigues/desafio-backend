package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.dto.CustomerUpdateDto
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.exception.EmailAlreadyUsedException
import java.util.UUID
import kotlin.jvm.Throws

interface CustomerUpdateInteractor {
    @Throws(EmailAlreadyUsedException::class)
    fun execute(id: UUID, customerUpdateDto: CustomerUpdateDto): Customer
}
