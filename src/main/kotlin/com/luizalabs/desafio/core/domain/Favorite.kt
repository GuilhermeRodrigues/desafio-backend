package com.luizalabs.desafio.core.domain

import com.luizalabs.desafio.provider.api.challenge.response.Product
import java.time.LocalDateTime
import java.util.UUID

data class Favorite(
    val id: UUID = UUID.randomUUID(),
    val favoritesList: FavoritesList,
    val product: Product,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var deletedAt: LocalDateTime? = null
)
