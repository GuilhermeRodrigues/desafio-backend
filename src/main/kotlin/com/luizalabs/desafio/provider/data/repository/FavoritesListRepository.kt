package com.luizalabs.desafio.provider.data.repository

import com.luizalabs.desafio.provider.data.table.FavoritesListTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FavoritesListRepository : JpaRepository<FavoritesListTable, UUID> {
    fun findByCustomerId(customerId: UUID): FavoritesListTable?
}
