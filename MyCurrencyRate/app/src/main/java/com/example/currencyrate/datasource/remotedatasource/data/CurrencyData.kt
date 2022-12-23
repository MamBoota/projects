package com.example.currencyrate.datasource.remotedatasource.data

data class CurrencyData(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)