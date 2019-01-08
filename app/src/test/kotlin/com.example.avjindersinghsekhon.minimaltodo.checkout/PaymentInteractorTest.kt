package com.example.avjindersinghsekhon.minimaltodo.checkout

import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.PaymentRepository
import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.RepositoryException
import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.TransactionUnsuccessfulException
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
class PaymentInteractorTest {

    @Mock private lateinit var presenter: PaymentPresenter
    @Mock private lateinit var repository: PaymentRepository
    @InjectMocks private lateinit var interactor: PaymentInteractor

    @Test
    fun givenAReceipt_whenLoadReceipt_shouldPresentReceipt() {
        // Given
        given(repository.loadReceipt("merchantCode", "transactionCode")).willReturn(
            Receipt(
                receiptNumber = "receiptNumber",
                transactionCode = "transactionCode",
                amount = BigDecimal("30.5"),
                status = "SUCCESSFUL"
            )
        )

        // When
        interactor.loadReceipt("merchantCode", "transactionCode")

        // Then
        then(presenter).should(only()).presentReceipt(Receipt(
            receiptNumber = "receiptNumber",
            transactionCode = "transactionCode",
            amount = BigDecimal("30.5"),
            status = "SUCCESSFUL"
        ))
    }

    @Test
    fun givenATransactionUnsuccessfulException_whenLoadReceipt_shouldPresentTransactionFailed() {
        // Given
        given(repository.loadReceipt("merchantCode", "transactionCode")).willThrow(
            TransactionUnsuccessfulException::class.java
        )

        // When
        interactor.loadReceipt("merchantCode", "transactionCode")

        // Then
        then(presenter).should(only()).presentTransactionFailed()
    }

    @Test
    fun givenARepositoryException_whenLoadReceipt_shouldPresentTransactionFailed() {
        // Given
        given(repository.loadReceipt("merchantCode", "transactionCode")).willThrow(
            RepositoryException::class.java
        )

        // When
        interactor.loadReceipt("merchantCode", "transactionCode")

        // Then
        then(presenter).should(only()).presentError()
    }

}
