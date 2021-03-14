package com.luizalabs.desafio.entrypoint.api.response

import io.swagger.annotations.ApiModelProperty
import java.util.UUID

data class FavoriteResponse(
    @ApiModelProperty(required = true, value = "ID do produto")
    val productId: UUID,

    @ApiModelProperty(required = true, value = "ID da lista de favoritos")
    val favoritesListId: UUID,

    @ApiModelProperty(required = true, value = "Data de inclusão do favorito", example = "11/03/2021")
    val addedAt: String
)
