package com.luizalabs.desafio.core.exception.impl

import com.luizalabs.desafio.core.exception.rest.RestException
import org.springframework.http.HttpStatus

open class InternalServerErrorException(
    override val developerMessage: String = "Internal Server Error"
) : RestException(developerMessage) {
    override val statusCode = HttpStatus.INTERNAL_SERVER_ERROR
}
