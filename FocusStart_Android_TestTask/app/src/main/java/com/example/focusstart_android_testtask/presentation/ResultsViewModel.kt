package com.example.focusstart_android_testtask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusstart_android_testtask.domain.GetBinActivityUseCase
import com.example.focusstart_android_testtask.entity.BinActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResultsViewModel @Inject constructor(
    private val getBinActivityUseCase: GetBinActivityUseCase
) : ViewModel() {

    private val _bin = MutableStateFlow<BinActivity?>(null)
    val bin = _bin.asStateFlow()

    fun reloadBinActivity(number: String) {
        viewModelScope.launch {
            _bin.value = getBinActivityUseCase.execute(number)
        }
    }
}