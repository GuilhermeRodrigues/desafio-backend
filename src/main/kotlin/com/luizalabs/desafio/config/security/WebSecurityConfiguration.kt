package com.luizalabs.desafio.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.luizalabs.desafio.core.exception.base.BaseExceptionResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
@Configuration
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Value("\${application.security.jwt-enabled}")
    private var jwtEnabled: Boolean = true

    @Value("\${application.security.jwt-secret-key}")
    private lateinit var jwtSecretKey: String

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var context: ApplicationContext

    @Throws(java.lang.Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(
                JwtInterceptorConfiguration(this.jwtEnabled, this.jwtSecretKey, this.context),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .exceptionHandling()
            .authenticationEntryPoint { _: HttpServletRequest?,
                response: HttpServletResponse,
                _: AuthenticationException? ->
                response.status = HttpStatus.UNAUTHORIZED.value()
                response.contentType = "application/json"
                response.writer.write(
                    this.mapper.writeValueAsString(
                        BaseExceptionResponse(
                            userMessage = "Unauthorized",
                            developerMessage = "Without permission to perform this action",
                            errorCode = 400001,
                            statusCode = HttpStatus.UNAUTHORIZED.value()
                        )
                    )
                )
            }
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/",
            "/docs",
            "/swagger",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/logger/health",
            "/actuator/**"
        )
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }
}
