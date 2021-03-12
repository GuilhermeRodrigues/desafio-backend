package com.luizalabs.desafio.annotation

import org.springframework.context.annotation.Configuration

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Configuration
annotation class ApiClient
