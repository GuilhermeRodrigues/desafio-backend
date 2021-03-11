package com.luizalabs.desafio.core.exception.base

data class BaseExceptionResponse(
    val userMessage: String? = null,
    val developerMessage: String? = null,
    val errorCode: Int? = null,
    val statusCode: Int
)
