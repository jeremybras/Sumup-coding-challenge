package com.example.avjindersinghsekhon.minimaltodo.checkout

import java.math.BigDecimal

data class Receipt(
    val receiptNumber: String,
    val transactionCode: String,
    val amount: BigDecimal,
    val status: String,
    val paymentType: String
)
