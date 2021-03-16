package com.luizalabs.desafio.entrypoint.api.response

import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class ResponseTest {
    @Test
    fun customerResponseTest() {
        val table = CustomerResponse::class.createMockInstance(useDefaultValues = true, generateNulls = false)
            .copy(
                updatedAt = "11/03/2021",
                deletedAt = "11/03/2021"
            )
        assertNotNull(table)
    }
}
