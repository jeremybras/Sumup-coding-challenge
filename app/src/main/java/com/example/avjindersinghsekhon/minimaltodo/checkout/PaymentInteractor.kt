package com.example.avjindersinghsekhon.minimaltodo.checkout

import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.PaymentRepository
import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.RepositoryException
import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.TransactionUnsuccessfulException

class PaymentInteractor(
    private val presenter: PaymentPresenter,
    private val repository: PaymentRepository
) {
    fun loadReceipt(merchantCode: String, transactionCode: String) {
        try {
            val receipt = repository.loadReceipt(merchantCode, transactionCode)
            presenter.presentReceipt(receipt)
        } catch (e: TransactionUnsuccessfulException) {
            presenter.presentTransactionFailed()
        } catch (e: RepositoryException) {
            presenter.presentError()
        }
    }
}
