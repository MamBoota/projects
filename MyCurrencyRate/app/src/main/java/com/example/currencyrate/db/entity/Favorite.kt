package com.example.currencyrate.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey val name: String
)
