package com.luizalabs.desafio.annotation.config

import com.luizalabs.desafio.config.security.WebSecurityConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@MustBeDocumented
@Import(WebSecurityConfiguration::class)
@Configuration
annotation class EnableWebSecurityConfiguration
