package com.example.currencyrate.presentation.mainactivity.domain

import com.example.currencyrate.datasource.remotedatasource.data.Rates
import com.example.currencyrate.datasource.remotedatasource.datasource.AppRemoteRepository
import com.example.currencyrate.db.dao.FavoriteDao
import javax.inject.Inject

class MainActivityInteractorImpl @Inject constructor(
    private val repository: AppRemoteRepository,
    private val favoriteDao: FavoriteDao
): MainActivityInteractor {

    override suspend fun getAllCurrency(base: String): Rates {
        return repository.getAllCurrency(base)
    }

    override suspend fun getListFavorite(): List<String> {
        return favoriteDao.getListFavorite()
    }

}