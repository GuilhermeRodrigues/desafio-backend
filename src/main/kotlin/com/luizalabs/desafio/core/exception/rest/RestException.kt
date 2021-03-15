package com.luizalabs.desafio.core.exception.rest

import com.luizalabs.desafio.core.exception.base.BaseExceptionResponse
import org.springframework.http.HttpStatus
import java.lang.RuntimeException

abstract class RestException(developer: String) : RuntimeException(developer) {
    abstract val developerMessage: String
    open val userMessage: String = developer
    open val errorCode: Int? = null
    abstract val statusCode: HttpStatus

    fun getBody(): BaseExceptionResponse {
        return BaseExceptionResponse(
            userMessage = userMessage,
            developerMessage = developerMessage,
            errorCode = errorCode,
            statusCode = statusCode.value()
        )
    }
}
