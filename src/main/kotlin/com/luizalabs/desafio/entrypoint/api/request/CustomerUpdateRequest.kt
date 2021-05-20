package com.luizalabs.desafio.entrypoint.api.request

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Pattern

data class CustomerUpdateRequest(
    @ApiModelProperty(required = false, value = "Nome do cliente")
    val name: String? = null,

    @ApiModelProperty(required = false, value = "E-mail do cliente")
    @field:Pattern(
        regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$",
        message = "É necessário um e-mail válido, ex: teste@gmail.com"
    )
    val email: String? = null
)
