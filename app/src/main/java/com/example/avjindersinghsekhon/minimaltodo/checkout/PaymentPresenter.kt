package com.example.avjindersinghsekhon.minimaltodo.checkout


interface PaymentPresenter {
    fun presentReceipt(receipt: Receipt)
}

interface PaymentView {
    fun displayReceipt(receiptViewModel: ReceiptViewModel)
}

class PaymentPresenterImpl(private val view: PaymentView) : PaymentPresenter {
    override fun presentReceipt(receipt: Receipt) {
        view.displayReceipt(
            ReceiptViewModel(
                receiptNumber = receipt.receiptNumber
            )
        )
    }
}

data class ReceiptViewModel(
    val receiptNumber: String
)
