package com.luizalabs.desafio.util

import org.springframework.data.domain.Page
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap

fun <T> Page<T>.toResponseEntity(
    headers: MultiValueMap<String, String> = HttpHeaders(),
    status: HttpStatus = HttpStatus.OK
): ResponseEntity<List<T>> {
    headers.add("_limit", this.size.toString())
    headers.add("_offset", (this.number * this.size).toString())
    headers.add("content-range", this.totalElements.toString())

    return ResponseEntity(this.content, headers, status)
}
