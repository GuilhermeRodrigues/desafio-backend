package com.luizalabs.desafio.entrypoint.api.response

import com.luizalabs.desafio.provider.api.product.response.Product
import io.swagger.annotations.ApiModelProperty
import java.util.UUID

data class FavoriteResponse(
    @ApiModelProperty(required = true, value = "ID do cliente")
    val customerId: UUID,

    @ApiModelProperty(required = true, value = "Favoritos")
    val favorites: List<Product>
)
