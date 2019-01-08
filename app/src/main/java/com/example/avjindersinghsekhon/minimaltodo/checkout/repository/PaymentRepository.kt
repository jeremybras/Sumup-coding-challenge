package com.example.avjindersinghsekhon.minimaltodo.checkout.repository

import com.example.avjindersinghsekhon.minimaltodo.checkout.Receipt
import java.io.IOException
import java.math.BigDecimal

interface PaymentRepository {
    @Throws(RepositoryException::class)
    fun loadReceipt(merchantCode: String, transactionCode: String): Receipt
}

class PaymentRepositoryImpl(private val service: ReceiptService) : PaymentRepository {

    companion object {
        private const val STATUS_SUCCESSFUL = "SUCCESSFUL"
    }

    @Throws(RepositoryException::class)
    override fun loadReceipt(merchantCode: String, transactionCode: String): Receipt {
        try {
            service.getReceipt(transactionCode, merchantCode).execute().body()?.let { receipt ->
                with(receipt.transaction_data) {
                    if (status != STATUS_SUCCESSFUL) {
                        throw TransactionUnsuccessfulException()
                    }
                    return Receipt(
                        receiptNumber = receipt_no,
                        transactionCode = transaction_code,
                        amount = BigDecimal(amount),
                        status = status
                    )
                }
            } ?: throw RepositoryException()
        } catch (io: IOException) {
            throw RepositoryException(cause = io)
        }
    }
}
