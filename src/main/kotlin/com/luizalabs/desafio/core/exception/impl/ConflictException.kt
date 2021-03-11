package com.luizalabs.desafio.core.exception.impl

import com.luizalabs.desafio.core.exception.rest.RestException
import org.springframework.http.HttpStatus

open class ConflictException(
    override val developerMessage: String = "Conflict"
) : RestException(developerMessage) {
    override val statusCode = HttpStatus.CONFLICT
}
