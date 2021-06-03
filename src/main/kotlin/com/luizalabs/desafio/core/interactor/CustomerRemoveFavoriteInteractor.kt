package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.dto.CustomerFavoriteDto
import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.core.exception.FavoriteNotFoundException
import java.util.UUID
import kotlin.jvm.Throws

interface CustomerRemoveFavoriteInteractor {
    @Throws(FavoriteNotFoundException::class)
    fun execute(customerId: UUID, customerFavoriteDto: CustomerFavoriteDto): Favorite
}
