package com.example.avjindersinghsekhon.minimaltodo.checkout.repository

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiptJson(
    val transaction_data: TransactionData
)

@JsonClass(generateAdapter = true)
data class TransactionData(
    val receipt_no: String,
    val transaction_code: String,
    val amount: String,
    val vat_amount: String,
    val tip_amount: String,
    val status: String,
    val payment_type: String,
    val customer_email: String
)
