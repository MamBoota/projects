package com.example.focusstart_android_testtask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultsViewModelFactory(private val resultsViewModel: ResultsViewModel) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {

            return resultsViewModel as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name")
    }
}