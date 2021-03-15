package com.luizalabs.desafio.util

import com.luizalabs.desafio.config.getObjectMapper

fun <T : Any> T.toJSON(): String? {
    val mapper = getObjectMapper()
    return mapper.writeValueAsString(this)
}
