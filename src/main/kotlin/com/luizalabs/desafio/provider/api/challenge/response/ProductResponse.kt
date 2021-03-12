package com.luizalabs.desafio.provider.api.challenge.response

import java.math.BigDecimal

data class ProductResponse(
    val meta: ProductMeta,
    val products: List<Product>
)

data class ProductMeta(
    val page_number: Int,
    val page_size: Int
)

data class Product(
    val price: BigDecimal,
    val image: String,
    val brand: String,
    val id: String,
    val title: String
)
