package com.luizalabs.desafio.mapper

import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.entrypoint.api.response.FavoriteResponse
import com.luizalabs.desafio.provider.data.table.FavoriteTable
import java.time.format.DateTimeFormatter

fun FavoriteTable.toCore(): Favorite {
    return Favorite(
        id = id,
        favoritesList = favoritesList.toCore(),
        productId = productId,
        addedAt = addedAt
    )
}

fun Favorite.toTable(): FavoriteTable {
    return FavoriteTable(
        id = id,
        favoritesList = favoritesList.toTable(),
        productId = productId,
        addedAt = addedAt
    )
}

fun Favorite.toFavoriteResponse(): FavoriteResponse {
    return FavoriteResponse(
        productId = productId,
        favoritesListId = favoritesList.id,
        addedAt = addedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    )
}
