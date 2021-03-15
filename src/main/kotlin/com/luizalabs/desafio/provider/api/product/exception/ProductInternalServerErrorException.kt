package com.luizalabs.desafio.provider.api.product.exception

import com.luizalabs.desafio.core.exception.impl.InternalServerErrorException

class ProductInternalServerErrorException : InternalServerErrorException("Não foi possível obter o produto")
