package com.luizalabs.desafio.entrypoint.api.request

import io.swagger.annotations.ApiModelProperty
import java.util.UUID
import javax.validation.constraints.NotNull

data class CustomerFavoriteRequest(
    @ApiModelProperty(required = true, value = "Id do produto")
    @field:NotNull
    val productId: UUID
)
