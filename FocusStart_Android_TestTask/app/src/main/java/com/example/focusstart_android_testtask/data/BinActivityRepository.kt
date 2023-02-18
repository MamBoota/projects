package com.example.focusstart_android_testtask.data

import com.example.focusstart_android_testtask.domain.SearchBinApi
import com.example.focusstart_android_testtask.entity.BinActivity
import javax.inject.Inject

class BinActivityRepository @Inject constructor(
    private val api: SearchBinApi
) {

    suspend fun getBinActivity(number: String): BinActivity {
        return api.getDataBin(number).body()!!
    }

}