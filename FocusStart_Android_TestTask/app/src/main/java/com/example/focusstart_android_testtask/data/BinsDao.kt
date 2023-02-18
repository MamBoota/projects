package com.example.focusstart_android_testtask.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.focusstart_android_testtask.entity.Bins
import kotlinx.coroutines.flow.Flow

@Dao
interface BinsDao {

    @Query("SELECT * FROM bins")
    fun getAll(): Flow<List<Bins>>

    @Query("SELECT * FROM bins WHERE bin is :newBin")
    suspend fun getBin(newBin: String): Bins?

    @Insert(entity = Bins::class)
    suspend fun insert(bins: Bins)

    @Update
    suspend fun update(bins: Bins)

    @Query("DELETE FROM bins")
    suspend fun delete()
}