package com.example.focusstart_android_testtask.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bins")
data class Bins(

    @PrimaryKey
    val bin: String,

    @ColumnInfo(name = "count")
    var count: Int = 1
)
