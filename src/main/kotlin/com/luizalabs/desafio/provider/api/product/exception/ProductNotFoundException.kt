package com.luizalabs.desafio.provider.api.product.exception

import com.luizalabs.desafio.core.exception.impl.NotFoundException

class ProductNotFoundException : NotFoundException("Produto não encontrado")
