package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.component.DataProvider
import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.core.gateway.FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
import com.luizalabs.desafio.core.gateway.FavoriteSaveGateway
import com.luizalabs.desafio.mapper.toCore
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.FavoriteRepository
import java.util.UUID

@DataProvider
class FavoriteDataProvider(
    private val repository: FavoriteRepository
) : FavoriteSaveGateway,
    FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway {

    override fun save(favorite: Favorite): Favorite {
        return this.repository
            .save(favorite.toTable())
            .toCore()
    }

    override fun findByFavoritesListIdAndDeletedAtIsNull(favoritesListId: UUID): List<Favorite>? {
        return this.repository
            .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId)
            ?.map { it.toCore() }
    }
}
