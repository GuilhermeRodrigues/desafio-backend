package com.luizalabs.desafio.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

fun getObjectMapper(): ObjectMapper = Jackson2ObjectMapperBuilder
    .json()
    .modules(JavaTimeModule(), KotlinModule())
    .dateFormat(StdDateFormat())
    .serializationInclusion(JsonInclude.Include.NON_NULL)
    .build()

@Configuration
class ObjectMapperConfiguration {
    @Bean
    fun mapper(): ObjectMapper = getObjectMapper()
}
