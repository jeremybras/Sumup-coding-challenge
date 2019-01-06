package com.example.avjindersinghsekhon.minimaltodo.checkout.repository

open class RepositoryException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause)

class TransactionUnsuccessful: RepositoryException()
