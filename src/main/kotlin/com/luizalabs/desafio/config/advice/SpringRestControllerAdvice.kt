package com.luizalabs.desafio.config.advice

import com.luizalabs.desafio.core.exception.base.BaseExceptionResponse
import com.luizalabs.desafio.core.exception.rest.RestException
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(1)
class SpringRestControllerAdvice {
    @ExceptionHandler(RestException::class)
    fun exceptions(error: RestException): ResponseEntity<BaseExceptionResponse> {
        return ResponseEntity
            .status(error.statusCode)
            .body(error.getBody())
    }

    @ExceptionHandler(Throwable::class)
    fun exceptionsGeneric(error: Throwable): ResponseEntity<BaseExceptionResponse> {
        return ResponseEntity
            .status(500)
            .body(
                BaseExceptionResponse(
                    errorCode = 5000,
                    statusCode = 500,
                    userMessage = error.message,
                    developerMessage = error.message
                )
            )
    }
}
