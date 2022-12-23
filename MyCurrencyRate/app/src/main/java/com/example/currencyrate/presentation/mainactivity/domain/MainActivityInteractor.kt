package com.example.currencyrate.presentation.mainactivity.domain

import com.example.currencyrate.datasource.remotedatasource.data.Rates

interface MainActivityInteractor {

    suspend fun getAllCurrency(base: String): Rates

    suspend fun getListFavorite(): List<String>
}