package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.FavoritesList

interface FavoritesListSaveGateway {
    fun save(favoritesList: FavoritesList): FavoritesList
}
