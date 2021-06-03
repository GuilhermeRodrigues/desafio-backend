package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.entity.Customer
import java.util.UUID

interface CustomerDeleteInteractor {
    fun execute(id: UUID): Customer
}
