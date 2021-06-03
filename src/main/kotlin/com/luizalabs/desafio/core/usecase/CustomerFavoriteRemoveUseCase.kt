package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.component.UseCase
import com.luizalabs.desafio.core.domain.dto.CustomerFavoriteDto
import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.core.exception.FavoriteNotFoundException
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
import com.luizalabs.desafio.core.gateway.FavoriteSaveGateway
import com.luizalabs.desafio.core.gateway.FavoritesListFindByCustomerIdGateway
import com.luizalabs.desafio.core.interactor.CustomerRemoveFavoriteInteractor
import java.time.LocalDateTime
import java.util.UUID

@UseCase
internal class CustomerFavoriteRemoveUseCase(
    private val customerFindByIdGateway: CustomerFindByIdGateway,
    private val favoritesListFindByCustomerIdGateway: FavoritesListFindByCustomerIdGateway,
    private val favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway: FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway,
    private val favoriteSaveGateway: FavoriteSaveGateway
) : CustomerRemoveFavoriteInteractor {
    override fun execute(customerId: UUID, customerFavoriteDto: CustomerFavoriteDto): Favorite {
        val customer = this.customerFindByIdGateway.findById(id = customerId)

        val favoritesList = this.favoritesListFindByCustomerIdGateway
            .findByCustomerId(customerId = customer.id) ?: throw FavoriteNotFoundException()

        val favorite = this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
            .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = favoritesList.id)
            ?.find { it.product.id == customerFavoriteDto.productId } ?: throw FavoriteNotFoundException()

        return favoriteSaveGateway.save(
            favorite.copy(
                deletedAt = LocalDateTime.now()
            )
        )
    }
}
