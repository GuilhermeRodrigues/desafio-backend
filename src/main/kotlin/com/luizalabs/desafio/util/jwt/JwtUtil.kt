package com.luizalabs.desafio.util.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import kotlin.collections.HashMap

class JwtUtil {
  companion object {
    fun getEncodedJwt(jwtSecretKey: String): String {
      return "Bearer " +
          Jwts.builder().setHeaderParam("typ", "JWT")
              .addClaims(object : HashMap<String?, Any?>() {
                init {
                  put("abc", "123")
                }
              }).signWith(SignatureAlgorithm.HS256, jwtSecretKey.toByteArray())
              // .setExpiration(Date(Date().time + 28800000L)) // 8h
              .compact()
    }

    fun isValidJwt(jwtSecretKey: String, authorization: String): Boolean {
      return try {
        Jwts.parser()
            .setSigningKey(jwtSecretKey.toByteArray())
            .parseClaimsJws(authorization.replace("Bearer ", ""))
        true
      } catch (t: Throwable) {
        false
      }
    }
  }
}
