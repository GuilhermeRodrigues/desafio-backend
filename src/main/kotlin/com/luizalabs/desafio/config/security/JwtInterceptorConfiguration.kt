package com.luizalabs.desafio.config.security

import com.luizalabs.desafio.annotation.ExcludeAuthentication
import com.luizalabs.desafio.util.jwt.JwtUtil
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerMapping
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtInterceptorConfiguration(
    private val jwtEnabled: Boolean,
    private val jwtSecretKey: String,
    private val context: ApplicationContext
) : OncePerRequestFilter() {
  @Throws(ServletException::class, IOException::class)
  override fun doFilterInternal(
      request: HttpServletRequest,
      response: HttpServletResponse,
      filterChain: FilterChain
  ) {
    var authorization: String

    if (!this.jwtEnabled) {
      authorization =
          JwtUtil.getEncodedJwt(this.jwtSecretKey).replace("Bearer ", "")
    } else {
      authorization =
          (request.getHeader("Authorization") ?: "").replace("Bearer ", "")

      try {
        val handlerRequest =
            this.context.getBean(HandlerMapping::class.java).getHandler(request)

        if (handlerRequest != null) {
          val handlerMethod = (handlerRequest.handler as HandlerMethod).method

          if (handlerMethod.isAnnotationPresent(ExcludeAuthentication::class.java) ||
              handlerMethod.declaringClass.isAnnotationPresent(ExcludeAuthentication::class.java)) {
            authorization =
                JwtUtil.getEncodedJwt(this.jwtSecretKey).replace("Bearer ", "")
          }
        }
      } catch (t: Throwable) {
        System.err.println("Unable to get Request Controller Method: $t")
      }
    }

    if (JwtUtil.isValidJwt(this.jwtSecretKey, authorization)) {
      SecurityContextHolder.getContext().authentication =
          UsernamePasswordAuthenticationToken(authorization, null, arrayListOf())
    }

    filterChain.doFilter(request, response)
  }
}
