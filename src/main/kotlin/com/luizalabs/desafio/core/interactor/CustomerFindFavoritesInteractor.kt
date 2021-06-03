package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.core.exception.FavoriteNotFoundException
import java.util.UUID
import kotlin.jvm.Throws

interface CustomerFindFavoritesInteractor {
    @Throws(FavoriteNotFoundException::class)
    fun execute(id: UUID): List<Favorite>
}
