package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.Favorite
import java.util.UUID

interface FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway {
    fun findByFavoritesListIdAndDeletedAtIsNull(favoritesListId: UUID): List<Favorite>?
}
