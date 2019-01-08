package com.example.avjindersinghsekhon.minimaltodo.checkout.repository

import com.example.avjindersinghsekhon.minimaltodo.checkout.Receipt
import com.example.avjindersinghsekhon.minimaltodo.common.DaggerMainComponent
import com.example.avjindersinghsekhon.minimaltodo.common.MainModule
import com.nhaarman.mockito_kotlin.mock
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class PaymentRepositoryImplTest {

    private lateinit var repository: PaymentRepositoryImpl

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        val moshiConverter = DaggerMainComponent.builder()
            .mainModule(MainModule(mock()))
            .build()
            .moshiConverter()

        val retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(moshiConverter)
            .build()

        repository = PaymentRepositoryImpl(retrofit.create(ReceiptService::class.java))
    }

    @Test
    fun givenAReceipt_whenLoadReceipt_shouldReturnReceipt() {
        // Given
        val inputStream = javaClass.classLoader.getResourceAsStream("receipt.json")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(Buffer().readFrom(inputStream))
        )

        // When
        val response = repository.loadReceipt("merchantCode", "transactionCode")

        // Then
        assertThat(response).isEqualTo(
            Receipt(
                receiptNumber = "132456789",
                transactionCode = "transactionCode123",
                amount = BigDecimal("123.90"),
                status = "SUCCESSFUL"
            )
        )
    }

    @Test(expected = RepositoryException::class)
    fun givenABadResponseCode_whenLoadReceipt_shouldReturnRepositoryException() {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        // When
        repository.loadReceipt("merchantCode", "transactionCode")
    }

    @Test(expected = TransactionUnsuccessfulException::class)
    fun givenAnUnsuccessfulResponse_whenLoadReceipt_shouldReturnTransactionUnsuccessfulException() {
        // Given
        val inputStream = javaClass.classLoader.getResourceAsStream("receiptUnsuccessful.json")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(Buffer().readFrom(inputStream))
        )

        // When
        repository.loadReceipt("merchantCode", "transactionCode")
    }
}
