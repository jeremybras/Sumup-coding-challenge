package com.example.avjindersinghsekhon.minimaltodo.checkout

import android.content.res.Resources
import com.example.avjindersinghsekhon.minimaltodo.R
import com.example.avjindersinghsekhon.minimaltodo.common.DecimalFormatter
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.only
import com.nhaarman.mockito_kotlin.then
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class PaymentPresenterImplTest {

    @Mock private lateinit var view: PaymentView
    @Mock private lateinit var resources: Resources
    @Mock private lateinit var currencyFormatter: DecimalFormatter
    @InjectMocks private lateinit var presenter: PaymentPresenterImpl

    @Test
    fun whenPresentReceipt_thenFormatValuesAndCallView() {
        // Given
        given(currencyFormatter.formatCurrency(BigDecimal(125.15))).willReturn(
            "125.15"
        )
        given(resources.getString(R.string.checkout_display_amount, "125.15")).willReturn(
            "125.15 €"
        )

        // When
        presenter.presentReceipt(
            Receipt(
                receiptNumber = "23",
                transactionCode = "32154",
                amount = BigDecimal(125.15),
                status = "SUCCESSFUL"
            )
        )

        // Then
        then(view).should(only()).displayReceipt(
            ReceiptViewModel(
                receiptNumber = "23",
                amount = "125.15 €"
            )
        )
    }

    @Test
    fun whenPresentError_shouldDisplayRepositoryErrorMessage() {
        // Given
        given(resources.getString(R.string.checkout_repository_error)).willReturn("errorMessage")
        // When
        presenter.presentError()

        // Then
        then(view).should(only()).displayError("errorMessage")
    }

    @Test
    fun whenPresentTransactionFailed_shouldDisplayErrorMessage() {
        // Given
        given(resources.getString(R.string.checkout_error)).willReturn("errorMessage")
        // When
        presenter.presentTransactionFailed()

        // Then
        then(view).should(only()).displayError("errorMessage")
    }
}
