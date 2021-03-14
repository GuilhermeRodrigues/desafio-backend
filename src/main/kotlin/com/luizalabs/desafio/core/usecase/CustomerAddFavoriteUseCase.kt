package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UseCase
import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.core.domain.FavoritesList
import com.luizalabs.desafio.core.exception.FavoriteAlreadyAddedException
import com.luizalabs.desafio.core.gateway.*
import com.luizalabs.desafio.core.interactor.CustomerAddFavoriteInteractor
import com.luizalabs.desafio.entrypoint.api.request.CustomerAddFavoriteRequest
import java.util.UUID

@UseCase
internal class CustomerAddFavoriteUseCase(
    private val customerFindByIdGateway: CustomerFindByIdGateway,
    private val favoritesListFindByCustomerIdGateway: FavoritesListFindByCustomerIdGateway,
    private val favoritesListSaveGateway: FavoritesListSaveGateway,
    private val favoriteFindByFavoritesListIdAndProductIdGateway: FavoriteFindByFavoritesListIdAndProductIdGateway,
    private val favoriteSaveGateway: FavoriteSaveGateway,
    private val productFindByIdGateway: ProductFindByIdGateway
) : CustomerAddFavoriteInteractor {
    override fun execute(customerId: UUID, customerAddFavoriteRequest: CustomerAddFavoriteRequest): Favorite {
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

        var favorite = this.favoriteFindByFavoritesListIdAndProductIdGateway
            .findByFavoritesListIdAndProductId(favoritesListId = favoritesList.id, productId = customerAddFavoriteRequest.productId)

        if (favorite != null) {
            throw FavoriteAlreadyAddedException()
        }

        val product = productFindByIdGateway.findById(id = customerAddFavoriteRequest.productId)

        favorite = this.favoriteSaveGateway.save(
            Favorite(
                productId = product.id,
                favoritesList = favoritesList
            )
        )

        return favorite
    }
}
