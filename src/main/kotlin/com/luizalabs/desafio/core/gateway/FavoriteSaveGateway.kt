package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.entity.Favorite

interface FavoriteSaveGateway {
    fun save(favorite: Favorite): Favorite
}
