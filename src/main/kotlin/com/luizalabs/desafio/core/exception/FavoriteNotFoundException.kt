package com.luizalabs.desafio.core.exception

import com.luizalabs.desafio.core.exception.impl.NotFoundException

class FavoriteNotFoundException : NotFoundException("Favorito não encontrado")
