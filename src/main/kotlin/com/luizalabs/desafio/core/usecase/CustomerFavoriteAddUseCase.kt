package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.component.UseCase
import com.luizalabs.desafio.core.domain.dto.CustomerFavoriteDto
import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.core.domain.entity.FavoritesList
import com.luizalabs.desafio.core.exception.FavoriteAlreadyAddedException
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
import com.luizalabs.desafio.core.gateway.FavoriteSaveGateway
import com.luizalabs.desafio.core.gateway.FavoritesListFindByCustomerIdGateway
import com.luizalabs.desafio.core.gateway.FavoritesListSaveGateway
import com.luizalabs.desafio.core.gateway.ProductFindByIdGateway
import com.luizalabs.desafio.core.interactor.CustomerAddFavoriteInteractor
import java.util.UUID

@UseCase
internal class CustomerFavoriteAddUseCase(
    private val customerFindByIdGateway: CustomerFindByIdGateway,
    private val favoritesListFindByCustomerIdGateway: FavoritesListFindByCustomerIdGateway,
    private val favoritesListSaveGateway: FavoritesListSaveGateway,
    private val favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway: FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway,
    private val favoriteSaveGateway: FavoriteSaveGateway,
    private val productFindByIdGateway: ProductFindByIdGateway
) : CustomerAddFavoriteInteractor {
    override fun execute(customerId: UUID, customerFavoriteDto: CustomerFavoriteDto): Favorite {
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
            ?.any { it.product.id == customerFavoriteDto.productId }

        if (favoriteAlreadyAdded == true) {
            throw FavoriteAlreadyAddedException()
        }

        val product = productFindByIdGateway.findById(id = customerFavoriteDto.productId)

        return favoriteSaveGateway.save(
            Favorite(
                favoritesList = favoritesList,
                product = product
            )
        )
    }
}
