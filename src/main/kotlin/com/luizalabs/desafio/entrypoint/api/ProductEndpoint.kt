package com.luizalabs.desafio.entrypoint.api

import com.luizalabs.desafio.annotation.component.Endpoint
import com.luizalabs.desafio.core.interactor.ProductFindAllInteractor
import com.luizalabs.desafio.core.interactor.ProductFindByIdInteractor
import com.luizalabs.desafio.provider.api.product.response.Product
import com.luizalabs.desafio.provider.api.product.response.ProductResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID

@Endpoint("/v1/products")
@Api(tags = ["Produtos"], description = "/v1/products")
class ProductEndpoint(
    private val productFindAllInteractor: ProductFindAllInteractor,
    private val productFindByIdInteractor: ProductFindByIdInteractor
) {
    @ApiOperation(value = "Listar os produtos")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 404, message = "Página não encontrada")
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

    @ApiOperation(value = "Buscar produto pelo id")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok"),
            ApiResponse(code = 404, message = "Produto não encontrado")
        ]
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable id: UUID): Product {
        return this
            .productFindByIdInteractor
            .execute(id)
    }
}
