package com.example.avjindersinghsekhon.minimaltodo.checkout


interface PaymentController {
    fun loadReceipt(merchantCode: String, transactionCode: String)
}

class PaymentControllerImpl(private val interactor: PaymentInteractor): PaymentController {
    override fun loadReceipt(merchantCode: String, transactionCode: String) {
        interactor.loadReceipt(merchantCode, transactionCode)
    }
}
