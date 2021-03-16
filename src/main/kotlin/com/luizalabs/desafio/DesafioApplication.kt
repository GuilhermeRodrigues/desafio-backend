package com.luizalabs.desafio

import com.luizalabs.desafio.annotation.EnableRestControllerAdvice
import com.luizalabs.desafio.annotation.EnableWebSecurityConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableRestControllerAdvice
@EnableWebSecurityConfiguration
class DesafioApplication

fun main(args: Array<String>) {
    runApplication<DesafioApplication>(*args)
}
