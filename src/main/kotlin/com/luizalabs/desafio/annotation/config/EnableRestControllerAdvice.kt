package com.luizalabs.desafio.annotation.config

import com.luizalabs.desafio.config.advice.SpringRestControllerAdvice
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@MustBeDocumented
@Import(SpringRestControllerAdvice::class)
@Configuration
annotation class EnableRestControllerAdvice
