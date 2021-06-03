package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.dto.CustomerFavoriteDto
import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.core.exception.FavoriteAlreadyAddedException
import java.util.UUID
import kotlin.jvm.Throws

interface CustomerAddFavoriteInteractor {
    @Throws(FavoriteAlreadyAddedException::class)
    fun execute(customerId: UUID, customerFavoriteDto: CustomerFavoriteDto): Favorite
}
