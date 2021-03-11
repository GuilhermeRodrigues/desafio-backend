package com.luizalabs.desafio.core.exception.impl

import com.luizalabs.desafio.core.exception.rest.RestException
import org.springframework.http.HttpStatus

open class NotFoundException(
    override val developerMessage: String = "Not Found"
) : RestException(developerMessage) {
    override val statusCode = HttpStatus.NOT_FOUND
}
