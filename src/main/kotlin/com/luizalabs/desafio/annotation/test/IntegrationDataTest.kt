package com.luizalabs.desafio.annotation.test

import com.luizalabs.desafio.annotation.test.UnitTest
import org.springframework.boot.test.context.SpringBootTest

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@SpringBootTest
@UnitTest
annotation class IntegrationDataTest
