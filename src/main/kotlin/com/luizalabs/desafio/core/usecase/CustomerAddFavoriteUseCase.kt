package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UseCase
import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.core.domain.FavoritesList
import com.luizalabs.desafio.core.exception.FavoriteAlreadyAddedException
import com.luizalabs.desafio.core.gateway.*
import com.luizalabs.desafio.core.interactor.CustomerAddFavoriteInteractor
import com.luizalabs.desafio.entrypoint.api.request.CustomerFavoriteRequest
import java.util.UUID

@UseCase
internal class CustomerAddFavoriteUseCase(
    private val customerFindByIdGateway: CustomerFindByIdGateway,
    private val favoritesListFindByCustomerIdGateway: FavoritesListFindByCustomerIdGateway,
    private val favoritesListSaveGateway: FavoritesListSaveGateway,
    private val favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway: FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway,
    private val favoriteSaveGateway: FavoriteSaveGateway,
    private val productFindByIdGateway: ProductFindByIdGateway
) : CustomerAddFavoriteInteractor {
    override fun execute(customerId: UUID, customerFavoriteRequest: CustomerFavoriteRequest): Favorite {
        val customer = this.customerFindByIdGateway.findById(id = customerId)

        var favoritesList = this.favoritesListFindByCustomerIdGateway
            .findByCustomerId(customerId = customer.id)

        if (favoritesList == null) {
            favoritesList = this.favoritesListSaveGateway
                .save(
                    FavoritesList(
                        customer = customer
                    )
                )
        }

        val favoriteAlreadyAdded = this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
            .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = favoritesList.id)
            ?.any { it.product.id == customerFavoriteRequest.productId }

        if (favoriteAlreadyAdded == true) {
            throw FavoriteAlreadyAddedException()
        }

        val product = productFindByIdGateway.findById(id = customerFavoriteRequest.productId)

        return favoriteSaveGateway.save(
            Favorite(
                favoritesList = favoritesList,
                product = product
            )
        )
    }
}
