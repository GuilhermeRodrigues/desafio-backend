package com.luizalabs.desafio.config.swagger

import com.luizalabs.desafio.util.jwt.JwtUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import java.net.URI

@Configuration
class SwaggerConfiguration {
    @Value("\${application.app}")
    private lateinit var app: String

    @Value("\${application.version}")
    private lateinit var version: String

    @Value("\${application.security.jwt-enabled}")
    private var jwtEnabled: Boolean = true

    @Value("\${application.security.jwt-secret-key}")
    private lateinit var jwtSecretKey: String

    @Bean
    fun getSpringCommonDocket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            /* .enable(this.env != Env.PRODUCTION) */
            .pathMapping("/")
            .apiInfo(this.getApiInfo())
            .forCodeGeneration(true)
            .genericModelSubstitutes(ResponseEntity::class.java)
            .securityContexts(
                listOf(
                    this.getSecurityContext()
                )
            )
            .securitySchemes(
                listOf(
                    ApiKey("JWT", "Authorization", "header")
                )
            )
            .useDefaultResponseMessages(false)
            .consumes(setOf("application/json"))
            .produces(setOf("application/json"))
            .select()
            .paths(PathSelectors.regex("/v[0-9]+/.*"))
            .apis(RequestHandlerSelectors.basePackage("com.luizalabs"))
            .build()
    }

    private val swaggerRoute = "swagger-ui/"

    @Bean
    fun getSpringCommonRouterFunction(): RouterFunction<ServerResponse>? {
        return router {
            GET("/") {
                ServerResponse.permanentRedirect(URI.create(swaggerRoute)).build()
            }

            GET("/docs") {
                ServerResponse.permanentRedirect(URI.create(swaggerRoute)).build()
            }

            GET("/swagger") {
                ServerResponse.permanentRedirect(URI.create(swaggerRoute)).build()
            }

            GET("/swagger-ui.html") {
                ServerResponse.permanentRedirect(URI.create(swaggerRoute)).build()
            }
        }
    }

    private fun getApiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title(this.app)
            .description(
                if (this.jwtEnabled) {
                    JwtUtil.getEncodedJwt(this.jwtSecretKey)
                } else ""
            )
            .version(this.version)
            .build()
    }

    private fun getSecurityContext(): SecurityContext {
        return SecurityContext.builder()
            .securityReferences(
                arrayListOf(
                    SecurityReference(
                        "JWT",
                        arrayOf(AuthorizationScope("global", "accessEverything"))
                    )
                )
            )
            .forPaths(PathSelectors.any())
            .build()
    }
}
