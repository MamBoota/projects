package com.example.currencyrate.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitFactory @Inject constructor() {

    private val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()

    fun <T> getApiInterface(apiClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl("https://api.apilayer.com/exchangerates_data/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiClass)
    }
}