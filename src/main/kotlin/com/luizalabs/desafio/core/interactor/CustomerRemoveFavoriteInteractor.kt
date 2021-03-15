package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.core.exception.FavoriteNotFoundException
import com.luizalabs.desafio.entrypoint.api.request.CustomerFavoriteRequest
import java.util.UUID
import kotlin.jvm.Throws

interface CustomerRemoveFavoriteInteractor {
    @Throws(FavoriteNotFoundException::class)
    fun execute(customerId: UUID, customerFavoriteRequest: CustomerFavoriteRequest): Favorite
}
