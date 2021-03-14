package com.luizalabs.desafio.core.domain

import java.time.LocalDateTime
import java.util.UUID

data class Favorite(
    val id: UUID = UUID.randomUUID(),
    val favoritesList: FavoritesList,
    val productId: UUID,
    val addedAt: LocalDateTime = LocalDateTime.now()
)
