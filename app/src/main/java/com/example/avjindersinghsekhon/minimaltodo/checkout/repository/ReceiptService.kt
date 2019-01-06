package com.example.avjindersinghsekhon.minimaltodo.checkout.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReceiptService {
    @GET("receipts/{transactionCode}")
    fun getReceipt(
        @Path("transactionCode") transactionCode: String,
        @Query("mid") merchantCode: String
    ): Call<ReceiptJson>
}
