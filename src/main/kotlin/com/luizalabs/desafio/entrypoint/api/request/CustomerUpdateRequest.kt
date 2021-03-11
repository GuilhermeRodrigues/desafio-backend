package com.luizalabs.desafio.entrypoint.api.request

import io.swagger.annotations.ApiModelProperty

data class CustomerUpdateRequest(
    @ApiModelProperty(required = false, value = "Nome do cliente")
    val name: String? = null,

    @ApiModelProperty(required = false, value = "E-mail do cliente")
    val email: String? = null
)
