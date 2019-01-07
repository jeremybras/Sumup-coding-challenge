package com.example.avjindersinghsekhon.minimaltodo.checkout

import com.nhaarman.mockito_kotlin.only
import com.nhaarman.mockito_kotlin.then
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class PaymentControllerImplTest {

    @Mock private lateinit var interactor: PaymentInteractor
    @InjectMocks private lateinit var controller: PaymentControllerImpl

    @Test
    fun whenLoadReceipt_shouldCallInteractor() {
        // When
        controller.loadReceipt("merchantCode", "transactionCode")

        // Then
        then(interactor).should(only()).loadReceipt("merchantCode", "transactionCode")
    }

}
