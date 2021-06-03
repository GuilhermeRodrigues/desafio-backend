package com.luizalabs.desafio.mapper

import com.luizalabs.desafio.core.domain.entity.FavoritesList
import com.luizalabs.desafio.provider.data.table.FavoritesListTable

fun FavoritesListTable.toCore(): FavoritesList {
    return FavoritesList(
        id = id,
        customer = customer.toCore()
    )
}

fun FavoritesList.toTable(): FavoritesListTable {
    return FavoritesListTable(
        id = id,
        customer = customer.toTable()
    )
}
