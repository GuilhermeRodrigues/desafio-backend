package com.luizalabs.desafio.entrypoint.api.request

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

data class CustomerRequest(
    @ApiModelProperty(required = true, value = "Nome do cliente")
    @field:NotBlank
    val name: String,

    @ApiModelProperty(required = true, value = "E-mail do cliente")
    @field:NotBlank
    val email: String
)
