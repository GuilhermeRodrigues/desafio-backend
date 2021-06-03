package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.entity.Favorite
import java.util.UUID

interface FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway {
    fun findByFavoritesListIdAndDeletedAtIsNull(favoritesListId: UUID): List<Favorite>?
}
