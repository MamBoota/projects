package com.example.currencyrate.datasource.remotedatasource.datasource

import com.example.currencyrate.datasource.remotedatasource.data.Rates

interface AppRemoteRepository {

    suspend fun getAllCurrency(base: String): Rates

}