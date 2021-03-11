package com.luizalabs.desafio.core.exception

import com.luizalabs.desafio.core.exception.impl.NotFoundException

class CustomerNotFoundException : NotFoundException("Customer not found")
