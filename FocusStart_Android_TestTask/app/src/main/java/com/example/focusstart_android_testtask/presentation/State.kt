package com.example.focusstart_android_testtask.presentation

sealed class State {
    object Loading : State()
    object Success : State()
}