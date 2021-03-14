package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.Favorite
import java.util.UUID

interface FavoriteFindByFavoritesListIdAndProductIdGateway {
    fun findByFavoritesListIdAndProductId(favoritesListId: UUID, productId: UUID): Favorite?
}
