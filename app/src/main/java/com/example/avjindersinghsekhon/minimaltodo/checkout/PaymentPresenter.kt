package com.example.avjindersinghsekhon.minimaltodo.checkout

import android.content.res.Resources
import com.example.avjindersinghsekhon.minimaltodo.R
import com.example.avjindersinghsekhon.minimaltodo.common.DecimalFormatter


interface PaymentPresenter {
    fun presentReceipt(receipt: Receipt)
    fun presentError()
    fun presentTransactionFailed()
}

interface PaymentView {
    fun displayReceipt(receiptViewModel: ReceiptViewModel)
    fun displayError(errorMessage: String)
}

class PaymentPresenterImpl(
    private val view: PaymentView,
    private val resources: Resources,
    private val currencyFormatter: DecimalFormatter
) : PaymentPresenter {

    override fun presentReceipt(receipt: Receipt) {
        view.displayReceipt(
            ReceiptViewModel(
                receiptNumber = receipt.receiptNumber,
                amount = resources.getString(
                    R.string.checkout_display_amount,
                    currencyFormatter.formatCurrency(receipt.amount)
                )
            )
        )
    }

    override fun presentError() {
        view.displayError(resources.getString(R.string.checkout_repository_error))
    }

    override fun presentTransactionFailed() {
        view.displayError(resources.getString(R.string.checkout_error))
    }
}

data class ReceiptViewModel(
    val receiptNumber: String,
    val amount: String
)
