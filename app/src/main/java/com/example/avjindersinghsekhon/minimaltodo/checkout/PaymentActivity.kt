package com.example.avjindersinghsekhon.minimaltodo.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.avjindersinghsekhon.minimaltodo.R
import com.sumup.merchant.Models.TransactionInfo
import com.sumup.merchant.api.SumUpAPI
import com.sumup.merchant.api.SumUpPayment
import kotlinx.android.synthetic.main.activity_payment.*
import java.math.BigDecimal


class PaymentActivity : AppCompatActivity() {

    companion object {
        private const val CHECKOUT_REQUEST_CODE = 156
        private const val DISPLAY_PAYMENT_INFO = 0
        private const val DISPLAY_PAYMENT_ERROR = 1
        private const val DISPLAY_PAYMENT_LOADER = 2
        private const val DISPLAY_PAYMENT_RECEIPT = 3

        @JvmStatic
        fun newIntent(context: Context): Intent = Intent(context, PaymentActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkoutButton.setOnClickListener {
            val amount = amountEditText.text.toString()
            val mail = mailEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            makePayment(amount, mail, phoneNumber)
        }
        tryAgainButton.setOnClickListener {
            screenTypeViewFlipper.displayedChild = DISPLAY_PAYMENT_INFO
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHECKOUT_REQUEST_CODE && data != null) {
            if (resultCode == 1) {
                setProgressLabel(R.string.checkout_retrieving_receipt)
                (data.extras?.getParcelable(SumUpAPI.Response.TX_INFO) as TransactionInfo).transactionCode
            } else {
                screenTypeViewFlipper.displayedChild = DISPLAY_PAYMENT_ERROR
                errorLabel.text = data.extras?.getString(SumUpAPI.Response.MESSAGE)
            }
        }
    }

    private fun displayReceipt() {
        screenTypeViewFlipper.displayedChild = DISPLAY_PAYMENT_RECEIPT
    }

    private fun makePayment(amount: String, mail: String, phoneNumber: String) {
        val payment = SumUpPayment.builder()
            .total(BigDecimal(amount))
            .currency(SumUpPayment.Currency.EUR)
            .receiptEmail(mail)
            .receiptSMS(phoneNumber)
            .skipSuccessScreen()
            .build()
        setProgressLabel(R.string.checkout_progress_payment)
        SumUpAPI.checkout(this, payment, CHECKOUT_REQUEST_CODE)
    }

    private fun setProgressLabel(checkout_progress_payment: Int) {
        screenTypeViewFlipper.displayedChild = DISPLAY_PAYMENT_LOADER
        progressLabel.text = getString(checkout_progress_payment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
