package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.component.UseCase
import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.core.exception.FavoriteNotFoundException
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
import com.luizalabs.desafio.core.gateway.FavoritesListFindByCustomerIdGateway
import com.luizalabs.desafio.core.interactor.CustomerFindFavoritesInteractor
import java.util.UUID

@UseCase
internal class CustomerFindFavoritesUseCase(
    private val customerFindByIdGateway: CustomerFindByIdGateway,
    private val favoritesListFindByCustomerIdGateway: FavoritesListFindByCustomerIdGateway,
    private val favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway: FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
) : CustomerFindFavoritesInteractor {
    override fun execute(id: UUID): List<Favorite> {
        val customer = this.customerFindByIdGateway.findById(id = id)

        val favoritesList = this.favoritesListFindByCustomerIdGateway
            .findByCustomerId(customerId = customer.id) ?: throw FavoriteNotFoundException()

        return favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
            .findByFavoritesListIdAndDeletedAtIsNull(
                favoritesListId = favoritesList.id
            ) ?: throw FavoriteNotFoundException()
    }
}
