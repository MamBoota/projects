package com.example.focusstart_android_testtask.di

import com.example.focusstart_android_testtask.domain.GetBinActivityUseCase
import com.example.focusstart_android_testtask.presentation.ResultsViewModel
import com.example.focusstart_android_testtask.presentation.ResultsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideResultsViewModel(binActivityUseCase: GetBinActivityUseCase): ResultsViewModel {
        return ResultsViewModel(binActivityUseCase)
    }

    @Provides
    fun provideResultsViewModelFactory(resultsViewModel: ResultsViewModel) : ResultsViewModelFactory {
        return ResultsViewModelFactory(resultsViewModel)
    }
}