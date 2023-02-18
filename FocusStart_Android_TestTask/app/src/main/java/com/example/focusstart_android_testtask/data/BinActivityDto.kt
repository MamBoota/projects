package com.example.focusstart_android_testtask.data

import com.example.focusstart_android_testtask.entity.BinActivity

data class BinActivityDto(
    override val number: Number,
    override val scheme: String,
    override val type: String,
    override val brand: String,
    override val prepaid: Boolean,
    override val country: Country,
    override val bank: Bank
) : BinActivity

data class Number(
    val length: Int,
    val luhn: Boolean
)

data class Country(
    val name: String,
    val latitude: Int,
    val longitude: Int
)

data class Bank(
    val name: String,
    val url: String,
    val phone: String,
    val city: String
)
