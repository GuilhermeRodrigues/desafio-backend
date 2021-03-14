package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.Favorite

interface FavoriteSaveGateway {
    fun save(favorite: Favorite): Favorite
}
