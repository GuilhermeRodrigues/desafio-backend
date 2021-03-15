package com.luizalabs.desafio.annotation

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.annotation.Profile

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@ExtendWith(MockitoExtension::class)
@Profile("test")
annotation class UnitTest
