package com.fredy.mysavings.Feature.Data.APIs.CurrencyModels

import com.fredy.mysavings.Feature.Data.APIs.CurrencyModels.Response.ProcessResponse
import com.fredy.mytest.Api.ApiCredentials
import com.fredy.mytest.Api.TabScannerModel.Response.ResultResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface TabScannerAPI {
    @GET(ApiCredentials.TabScanner.GET_CREDIT)
    fun getCredit(@Header("apikey") apiKey: String = ApiCredentials.TabScanner.API_KEY): Call<Int>
    @Multipart
    @POST(ApiCredentials.TabScanner.POST_RECEIPT)
    fun processReceipt(
        @Part image: MultipartBody.Part,
        @Header("apikey") apiKey: String = ApiCredentials.TabScanner.API_KEY
    ): Call<ProcessResponse>
    @GET(ApiCredentials.TabScanner.GET_RESULT+"/{token}")
    fun getResult(
        @Path("token") token: String,
        @Header("apikey") apiKey: String  = ApiCredentials.TabScanner.API_KEY
    ): Call<ResultResponse>
}
