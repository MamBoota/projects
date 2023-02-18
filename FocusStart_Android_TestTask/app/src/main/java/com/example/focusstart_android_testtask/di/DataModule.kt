package com.example.focusstart_android_testtask.di

import com.example.focusstart_android_testtask.data.BinActivityRepository
import com.example.focusstart_android_testtask.domain.RetrofitInstance
import com.example.focusstart_android_testtask.domain.SearchBinApi
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideRetrofitInstance(): RetrofitInstance {
        return RetrofitInstance
    }

    @Provides
    fun provideSearchBniApi(retrofit: RetrofitInstance): SearchBinApi {
        return retrofit.searchBinApi
    }

    @Provides
    fun provideUsefulActivityRepository(api: SearchBinApi): BinActivityRepository {
        return BinActivityRepository(api)
    }
}