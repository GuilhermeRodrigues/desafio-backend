package com.luizalabs.desafio.provider.data.repository

import com.luizalabs.desafio.provider.data.table.FavoriteTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FavoriteRepository : JpaRepository<FavoriteTable, UUID> {
    fun findByFavoritesListIdAndProductId(favoritesListId: UUID, productId: UUID): FavoriteTable?
}
