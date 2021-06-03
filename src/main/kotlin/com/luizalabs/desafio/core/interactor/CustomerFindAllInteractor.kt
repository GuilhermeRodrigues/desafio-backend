package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.entity.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomerFindAllInteractor {
    fun execute(page: Pageable): Page<Customer>
}
