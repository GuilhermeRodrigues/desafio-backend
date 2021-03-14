package com.luizalabs.desafio.provider.api.challenge.exception

import com.luizalabs.desafio.core.exception.impl.NotFoundException

class ProductNotFoundException : NotFoundException("Produto não encontrado")
