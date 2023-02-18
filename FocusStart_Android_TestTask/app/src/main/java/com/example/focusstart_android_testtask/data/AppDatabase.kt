package com.example.focusstart_android_testtask.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.focusstart_android_testtask.entity.Bins

@Database(entities = [Bins::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): BinsDao
}