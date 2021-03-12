package com.luizalabs.desafio.core.exception

import com.luizalabs.desafio.core.exception.impl.ConflictException

class EmailAlreadyUsedException : ConflictException("Já existe um cliente com este e-mail")
