package com.example.currencyrate.presentation.recyclerview.domain

import com.example.currencyrate.db.dao.FavoriteDao
import com.example.currencyrate.db.entity.Favorite
import javax.inject.Inject

class RecyclerInteractorImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : RecyclerInteractor {

    override suspend fun insertFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        favoriteDao.deleteFavorite(favorite)
    }

    override suspend fun favoriteExists(name: String): Boolean {
        return favoriteDao.favoriteExists(name)
    }


}