package com.luizalabs.desafio.mapper

import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.entrypoint.api.request.CustomerCreateRequest
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

fun CustomerCreateRequest.toCore(): Customer {
    return Customer(
        name = name,
        email = email
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
