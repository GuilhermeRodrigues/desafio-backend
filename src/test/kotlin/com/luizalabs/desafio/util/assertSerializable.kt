package com.luizalabs.desafio.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import kotlin.reflect.KClass

fun <T : Any> KClass<T>.assertSerializable() {
    val mock = this.createMockInstance()
    val json = mock.toJSON()
    val obj = json?.toObject(this)
    assertNotNull(json)
    assertNotNull(obj)
    assertEquals(mock, obj)
}
