package com.example.avjindersinghsekhon.minimaltodo.checkout.repository

class RepositoryException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause)
