package com.example.currencyrate.presentation.recyclerview.domain

import com.example.currencyrate.db.entity.Favorite

interface RecyclerInteractor {


    suspend fun insertFavorite(favorite: Favorite)

    suspend fun deleteFavorite(favorite: Favorite)

    suspend fun favoriteExists(name: String): Boolean

}