package com.example.focusstart_android_testtask.di

import com.example.focusstart_android_testtask.data.BinActivityRepository
import com.example.focusstart_android_testtask.domain.GetBinActivityUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetBinActivityUseCase(
        repository: BinActivityRepository
    ): GetBinActivityUseCase {
        return GetBinActivityUseCase(repository)
    }
}