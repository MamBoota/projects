package com.example.focusstart_android_testtask.domain

import com.example.focusstart_android_testtask.data.BinActivityRepository
import com.example.focusstart_android_testtask.entity.BinActivity
import javax.inject.Inject

class GetBinActivityUseCase @Inject constructor(
    private val repository: BinActivityRepository
) {

    suspend fun execute(number: String): BinActivity {
        return repository.getBinActivity(number)
    }
}