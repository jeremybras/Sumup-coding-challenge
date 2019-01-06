package com.example.avjindersinghsekhon.minimaltodo.checkout.repository

import com.example.avjindersinghsekhon.minimaltodo.checkout.Receipt
import java.io.IOException


interface PaymentRepository {
    fun loadReceipt(merchantCode: String, transactionCode: String): Receipt
}

class PaymentRepositoryImpl(private val service: ReceiptService) : PaymentRepository {

    @Throws(RepositoryException::class)
    override fun loadReceipt(merchantCode: String, transactionCode: String): Receipt {
        try {
            service.getReceipt(transactionCode, merchantCode).execute().body()?.let { receipt ->
                with (receipt.transaction_data) {
                    return Receipt(
                        receiptNumber = receipt_no,
                        transactionCode = transaction_code,
                        amount = amount,
                        vatAmount = vat_amount,
                        tipAmount = tip_amount,
                        status = status,
                        paymentType = payment_type,
                        customerEmail = customer_email
                    )
                }
            } ?: throw RepositoryException()
        } catch (io: IOException) {
            throw RepositoryException(cause = io)
        }
    }
}
