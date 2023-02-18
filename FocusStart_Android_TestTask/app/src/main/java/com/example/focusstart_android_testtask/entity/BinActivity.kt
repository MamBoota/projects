package com.example.focusstart_android_testtask.entity

import com.example.focusstart_android_testtask.data.Bank
import com.example.focusstart_android_testtask.data.Country

interface BinActivity {
    val number: com.example.focusstart_android_testtask.data.Number
    val scheme: String
    val type: String
    val brand: String
    val prepaid: Boolean
    val country: Country
    val bank: Bank
}