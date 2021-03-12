package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomerFindAllGateway {
    fun findAll(page: Pageable): Page<Customer>
}
