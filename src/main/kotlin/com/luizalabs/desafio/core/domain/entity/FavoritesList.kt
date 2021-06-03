package com.luizalabs.desafio.core.domain.entity

import java.util.UUID

data class FavoritesList(
    val id: UUID = UUID.randomUUID(),
    val customer: Customer
)
