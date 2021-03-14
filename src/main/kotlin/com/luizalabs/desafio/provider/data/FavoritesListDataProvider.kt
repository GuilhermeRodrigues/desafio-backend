package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.DataProvider
import com.luizalabs.desafio.core.domain.FavoritesList
import com.luizalabs.desafio.core.gateway.FavoritesListFindByCustomerIdGateway
import com.luizalabs.desafio.core.gateway.FavoritesListSaveGateway
import com.luizalabs.desafio.mapper.toCore
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.FavoritesListRepository
import java.util.UUID

@DataProvider
class FavoritesListDataProvider(
    private val repository: FavoritesListRepository
) : FavoritesListFindByCustomerIdGateway,
    FavoritesListSaveGateway {

    override fun findByCustomerId(customerId: UUID): FavoritesList? {
        return this.repository
            .findByCustomerId(customerId)
            ?.toCore()
    }

    override fun save(favoritesList: FavoritesList): FavoritesList {
        return this.repository
            .save(favoritesList.toTable())
            .toCore()
    }
}
