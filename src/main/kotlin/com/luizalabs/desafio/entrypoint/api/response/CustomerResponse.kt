package com.luizalabs.desafio.entrypoint.api.response

import io.swagger.annotations.ApiModelProperty
import java.util.UUID

data class CustomerResponse(
    @ApiModelProperty(required = true, value = "ID do cliente")
    val id: UUID,

    @ApiModelProperty(required = true, value = "Nome do cliente")
    val name: String,

    @ApiModelProperty(required = true, value = "E-mail do cliente")
    val email: String,

    @ApiModelProperty(required = true, value = "Data de criação do registro", example = "11/03/2021")
    val createdAt: String,

    @ApiModelProperty(required = true, value = "Data de atualização do registro", example = "11/03/2021")
    val updatedAt: String
)
