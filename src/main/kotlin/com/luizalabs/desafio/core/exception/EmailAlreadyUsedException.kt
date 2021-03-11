package com.luizalabs.desafio.core.exception

import com.luizalabs.desafio.core.exception.impl.ConflictException

class EmailAlreadyUsedException : ConflictException("There is already a customer with this email")
