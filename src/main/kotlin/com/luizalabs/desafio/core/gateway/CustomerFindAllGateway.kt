package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.exception.PageNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import kotlin.jvm.Throws

interface CustomerFindAllGateway {
    @Throws(PageNotFoundException::class)
    fun findAll(page: Pageable): Page<Customer>
}
