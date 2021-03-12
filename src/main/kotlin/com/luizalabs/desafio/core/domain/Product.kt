package com.luizalabs.desafio.core.domain

import java.math.BigDecimal

data class Product(
    val price: BigDecimal,
    val image: String,
    val brand: String,
    val id: String,
    val title: String
)
