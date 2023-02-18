package com.example.focusstart_android_testtask.di

import com.example.focusstart_android_testtask.presentation.ResultsViewModelFactory
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class
    ]
)
interface AppComponent {

    fun mainViewModelFactory(): ResultsViewModelFactory

}