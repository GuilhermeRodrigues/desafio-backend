package com.luizalabs.desafio.entrypoint.api.request

import io.swagger.annotations.ApiModelProperty
import java.util.UUID
import javax.validation.constraints.NotBlank

data class CustomerFavoriteRequest(
    @ApiModelProperty(required = true, value = "Id do produto")
    @field:NotBlank
    val productId: UUID
)
