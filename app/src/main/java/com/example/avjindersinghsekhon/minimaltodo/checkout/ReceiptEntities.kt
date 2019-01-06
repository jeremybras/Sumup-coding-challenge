package com.example.avjindersinghsekhon.minimaltodo.checkout

data class Receipt(
    val receiptNumber: String,
    val transactionCode: String,
    val amount: String,
    val vatAmount: String,
    val tipAmount: String,
    val status: String,
    val paymentType: String,
    val customerEmail: String
)
