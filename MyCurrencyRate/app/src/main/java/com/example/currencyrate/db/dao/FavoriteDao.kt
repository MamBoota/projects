package com.example.currencyrate.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyrate.db.entity.Favorite

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT name FROM favorite")
    suspend fun getListFavorite(): List<String>

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE name = :name)")
    suspend fun favoriteExists(name: String): Boolean

}