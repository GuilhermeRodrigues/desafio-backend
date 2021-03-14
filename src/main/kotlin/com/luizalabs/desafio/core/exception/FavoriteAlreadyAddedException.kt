package com.luizalabs.desafio.core.exception

import com.luizalabs.desafio.core.exception.impl.ConflictException

class FavoriteAlreadyAddedException : ConflictException("Produto já adicionado como favorito")
