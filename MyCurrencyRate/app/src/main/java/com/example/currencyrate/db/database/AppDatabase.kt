package com.example.currencyrate.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyrate.app.VERSION_DATABASE
import com.example.currencyrate.db.dao.FavoriteDao
import com.example.currencyrate.db.entity.Favorite


@Database(entities = [Favorite::class], version = VERSION_DATABASE)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}