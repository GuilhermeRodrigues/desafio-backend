package com.luizalabs.desafio

import com.luizalabs.desafio.annotation.config.EnableRestControllerAdvice
import com.luizalabs.desafio.annotation.config.EnableSwaggerConfiguration
import com.luizalabs.desafio.annotation.config.EnableWebSecurityConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableRestControllerAdvice
@EnableWebSecurityConfiguration
@EnableSwaggerConfiguration
class DesafioApplication

fun main(args: Array<String>) {
    runApplication<DesafioApplication>(*args)
}
