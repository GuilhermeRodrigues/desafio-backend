package com.luizalabs.desafio.entrypoint.api

import com.luizalabs.desafio.annotation.Endpoint
import com.luizalabs.desafio.core.interactor.ProductFindAllInteractor
import com.luizalabs.desafio.provider.api.challenge.response.ProductResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus

@Endpoint("/v1/products")
@Api(tags = ["Produtos"], description = "/v1/products")
class ProductEndpoint(
    private val productFindAllInteractor: ProductFindAllInteractor
) {
    @ApiOperation(value = "Listar os produtos")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Produto encontrado com sucesso"),
            ApiResponse(code = 500, message = "Não foi possível obter o produto")
        ]
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(
        @RequestParam(required = false, name = "_page")
        page: Int? = null
    ): ProductResponse {
        return this
            .productFindAllInteractor
            .execute(page ?: 0)
    }
}
