package com.luizalabs.desafio.util

import com.tyro.oss.arbitrater.arbitrater
import kotlin.random.Random
import kotlin.reflect.KClass

fun <T : Any> KClass<T>.createMockInstance(useDefaultValues: Boolean = true, generateNulls: Boolean = true): T {
    return this.arbitrater()
        .generateNulls(generateNulls)
        .useDefaultValues(useDefaultValues)
        .createInstance()
}

fun <T : Any> KClass<T>.createMockInstances(
    size: Int = Random.nextInt(5, 20),
    useDefaultValues: Boolean = true,
    genarateNulls: Boolean = true
): List<T> {
    return List(size) {
        this.createMockInstance(useDefaultValues, genarateNulls)
    }
}
