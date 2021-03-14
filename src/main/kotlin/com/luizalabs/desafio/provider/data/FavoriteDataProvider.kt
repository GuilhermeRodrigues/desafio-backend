package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.DataProvider
import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.core.gateway.FavoriteFindByFavoritesListIdAndProductIdGateway
import com.luizalabs.desafio.core.gateway.FavoriteSaveGateway
import com.luizalabs.desafio.mapper.toCore
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.FavoriteRepository
import java.util.UUID

@DataProvider
class FavoriteDataProvider(
    private val repository: FavoriteRepository
) : FavoriteFindByFavoritesListIdAndProductIdGateway,
    FavoriteSaveGateway {

    override fun findByFavoritesListIdAndProductId(favoritesListId: UUID, productId: UUID): Favorite? {
        return this.repository
            .findByFavoritesListIdAndProductId(favoritesListId, productId)
            ?.toCore()
    }

    override fun save(favorite: Favorite): Favorite {
        return this.repository
            .save(favorite.toTable())
            .toCore()
    }
}
