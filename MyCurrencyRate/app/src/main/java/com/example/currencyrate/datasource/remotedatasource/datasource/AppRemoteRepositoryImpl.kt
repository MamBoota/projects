package com.example.currencyrate.datasource.remotedatasource.datasource

import com.example.currencyrate.datasource.remotedatasource.data.Rates
import javax.inject.Inject

class AppRemoteRepositoryImpl @Inject constructor(
    private val currencyAPI: CurrencyAPI
) : AppRemoteRepository {

    override suspend fun getAllCurrency(base: String): Rates {
        return currencyAPI.getAllCurrency(base = base).body()!!.rates
    }

}