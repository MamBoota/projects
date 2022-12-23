package com.example.currencyrate.datasource.remotedatasource.datasource

import com.example.currencyrate.app.API_KEY
import com.example.currencyrate.datasource.remotedatasource.data.CurrencyData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.text.DateFormatSymbols

interface CurrencyAPI {


    @GET("latest")
    suspend fun getAllCurrency(
        @Header("apikey") apikey: String = API_KEY,
        @Query("base") base: String
    ): Response<CurrencyData>


}