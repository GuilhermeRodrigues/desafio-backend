package com.luizalabs.desafio

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class DesafioApplicationTest {
    @Test
    fun createClass() {
        val app = DesafioApplication()
        assertNotNull(app)
    }

    @Test
    fun startMain() {
        assertNotNull(main(arrayOf()))
    }
}
