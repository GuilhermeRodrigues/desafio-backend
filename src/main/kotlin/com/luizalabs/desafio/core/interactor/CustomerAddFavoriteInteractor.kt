package com.luizalabs.desafio.core.interactor

import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.core.exception.FavoriteAlreadyAddedException
import com.luizalabs.desafio.entrypoint.api.request.CustomerAddFavoriteRequest
import java.util.UUID
import kotlin.jvm.Throws

interface CustomerAddFavoriteInteractor {
    @Throws(FavoriteAlreadyAddedException::class)
    fun execute(customerId: UUID, customerAddFavoriteRequest: CustomerAddFavoriteRequest): Favorite
}
