package com.luizalabs.desafio.entrypoint.api.request

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class CustomerCreateRequest(
    @ApiModelProperty(required = true, value = "Nome do cliente")
    @field:NotBlank
    val name: String,

    @ApiModelProperty(required = true, value = "E-mail do cliente")
    @field:Pattern(
        regexp = "/^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9]" +
            "(?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$/",
        message = "É necessário um e-mail válido, ex: teste@gmail.com"
    )
    @field:NotBlank
    val email: String
)
