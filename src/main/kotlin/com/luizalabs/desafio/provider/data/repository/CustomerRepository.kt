package com.luizalabs.desafio.provider.data.repository

import com.luizalabs.desafio.provider.data.table.CustomerTable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CustomerRepository : JpaRepository<CustomerTable, UUID> {
    fun findByEmail(email: String): CustomerTable?

    override fun findAll(pageable: Pageable): Page<CustomerTable>
}
