package com.luizalabs.desafio.core.gateway

import com.luizalabs.desafio.core.domain.entity.FavoritesList
import java.util.UUID

interface FavoritesListFindByCustomerIdGateway {
    fun findByCustomerId(customerId: UUID): FavoritesList?
}
