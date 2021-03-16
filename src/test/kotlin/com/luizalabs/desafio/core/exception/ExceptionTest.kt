package com.luizalabs.desafio.core.exception

import com.luizalabs.desafio.core.exception.base.BaseExceptionResponse
import com.luizalabs.desafio.core.exception.impl.ConflictException
import com.luizalabs.desafio.core.exception.impl.InternalServerErrorException
import com.luizalabs.desafio.core.exception.impl.NotFoundException
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExceptionTest {
    @Test
    fun `teste da base exception`() {
        Assertions.assertNotNull(
            BaseExceptionResponse::class.createMockInstance()
        )
        Assertions.assertNotNull(
            BaseExceptionResponse::class.createMockInstance()
                .copy(
                    statusCode = 500
                ).statusCode
        )
        Assertions.assertNotNull(
            BaseExceptionResponse::class.createMockInstance()
                .copy(
                    userMessage = "Internal server error"
                ).userMessage
        )
        Assertions.assertNotNull(
            BaseExceptionResponse::class.createMockInstance()
                .copy(
                    developerMessage = "Internal server error"
                ).developerMessage
        )
        Assertions.assertNotNull(
            BaseExceptionResponse::class.createMockInstance()
                .copy(
                    errorCode = 50000
                ).errorCode
        )
    }

    @Test
    fun `teste outras exceções`() {
        Assertions.assertNotNull(
            ConflictException::class.createMockInstance()
        )
        Assertions.assertNotNull(
            ConflictException::class.createMockInstance().getBody()
        )

        Assertions.assertNotNull(
            InternalServerErrorException::class.createMockInstance()
        )
        Assertions.assertNotNull(
            InternalServerErrorException::class.createMockInstance().getBody()
        )

        Assertions.assertNotNull(
            NotFoundException::class.createMockInstance()
        )
        Assertions.assertNotNull(
            NotFoundException::class.createMockInstance().getBody()
        )
    }
}
