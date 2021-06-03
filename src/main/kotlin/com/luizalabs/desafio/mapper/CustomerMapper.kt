package com.luizalabs.desafio.mapper

import com.luizalabs.desafio.core.domain.dto.CustomerCreateDto
import com.luizalabs.desafio.core.domain.dto.CustomerFavoriteDto
import com.luizalabs.desafio.core.domain.dto.CustomerUpdateDto
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.entrypoint.api.request.CustomerCreateRequest
import com.luizalabs.desafio.entrypoint.api.request.CustomerFavoriteRequest
import com.luizalabs.desafio.entrypoint.api.request.CustomerUpdateRequest
import com.luizalabs.desafio.entrypoint.api.response.CustomerResponse
import com.luizalabs.desafio.provider.data.table.CustomerTable
import java.time.format.DateTimeFormatter

fun CustomerTable.toCore(): Customer {
    return Customer(
        id = id,
        name = name,
        email = email,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
}

fun Customer.toTable(): CustomerTable {
    return CustomerTable(
        id = id,
        name = name,
        email = email,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
}

fun Customer.toCustomerResponse(): CustomerResponse {
    return CustomerResponse(
        id = id,
        name = name,
        email = email,
        createdAt = createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        updatedAt = if (updatedAt != null) updatedAt!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) else null,
        deletedAt = if (deletedAt != null) deletedAt!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) else null
    )
}

fun CustomerCreateDto.toCore(): Customer {
    return Customer(
        name = name,
        email = email
    )
}

fun CustomerCreateRequest.toDto(): CustomerCreateDto {
    return CustomerCreateDto(
        name = name,
        email = email
    )
}

fun CustomerUpdateRequest.toDto(): CustomerUpdateDto {
    return CustomerUpdateDto(
        name = name,
        email = email
    )
}

fun CustomerFavoriteRequest.toDto(): CustomerFavoriteDto {
    return CustomerFavoriteDto(
        productId = productId
    )
}
