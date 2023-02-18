package com.example.focusstart_android_testtask.domain

import com.example.focusstart_android_testtask.data.BinActivityDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://lookup.binlist.net/"

object RetrofitInstance {
    private val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val searchBinApi: SearchBinApi = retrofit.create(SearchBinApi::class.java)
}

interface SearchBinApi {
    @GET("{number}")
    suspend fun getDataBin(
        @Path("number") number: String
    ): Response<BinActivityDto>
}