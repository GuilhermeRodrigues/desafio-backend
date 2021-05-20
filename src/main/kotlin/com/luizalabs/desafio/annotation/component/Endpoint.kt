package com.luizalabs.desafio.annotation.component

import org.springframework.core.annotation.AliasFor
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
@RestController
@RequestMapping
annotation class Endpoint(
    @get:AliasFor(attribute = "value", annotation = RequestMapping::class)
    val path: String
)
