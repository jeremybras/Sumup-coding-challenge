package com.example.avjindersinghsekhon.minimaltodo.checkout

import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.PaymentRepository

class PaymentInteractor(
    private val presenter: PaymentPresenter,
    private val repository: PaymentRepository
) {
    fun loadReceipt(merchantCode: String, transactionCode: String) {
//        val receipt = repository.loadReceipt(merchantCode, transactionCode)
//        presenter.presentReceipt(receipt)
        presenter.presentReceipt(
            Receipt(
                receiptNumber = "",
                transactionCode = "",
                amount = "",
                vatAmount = "",
                tipAmount = "",
                status = "",
                paymentType = "",
                customerEmail = ""
            )
        )
    }
}
