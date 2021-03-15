package com.luizalabs.desafio.mapper

import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.entrypoint.api.response.FavoriteResponse
import com.luizalabs.desafio.provider.api.challenge.response.Product
import com.luizalabs.desafio.provider.data.table.FavoriteTable

fun FavoriteTable.toCore(): Favorite {
    return Favorite(
        id = id,
        favoritesList = favoritesList.toCore(),
        product = product,
        createdAt = createdAt,
        deletedAt = deletedAt
    )
}

fun Favorite.toTable(): FavoriteTable {
    return FavoriteTable(
        id = id,
        favoritesList = favoritesList.toTable(),
        product = product,
        createdAt = createdAt,
        deletedAt = deletedAt
    )
}

fun Favorite.toFavoriteResponse(favorites: List<Product>): FavoriteResponse {
    return FavoriteResponse(
        customerId = favoritesList.customer.id,
        favorites = favorites
    )
}
