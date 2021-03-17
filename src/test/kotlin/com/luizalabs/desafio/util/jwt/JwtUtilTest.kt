package com.luizalabs.desafio.util.jwt

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class JwtUtilTest {
    @Test
    fun `valid jwt`() {
        Assertions.assertTrue(
            JwtUtil.isValidJwt(
                "1234567890-desafio1234567890-1234567890",
                JwtUtil.getEncodedJwt("1234567890-desafio1234567890-1234567890")
            )
        )
    }

    @Test
    fun `invalid jwt`() {
        Assertions.assertFalse(
            JwtUtil.isValidJwt(
                "0987654321-desafio0987654321-0987654321",
                JwtUtil.getEncodedJwt("1234567890-desafio1234567890-1234567890")
            )
        )
    }
}
