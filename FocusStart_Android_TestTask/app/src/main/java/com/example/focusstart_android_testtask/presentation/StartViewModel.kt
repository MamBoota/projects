package com.example.focusstart_android_testtask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.focusstart_android_testtask.data.BinsDao
import com.example.focusstart_android_testtask.entity.Bins
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StartViewModel(private val binsDao: BinsDao) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()
    val allBin = binsDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onSearchBtn(bin: String): Boolean {
        val error = bin.length == 8
        return if (error) {
            if (bin.matches(Regex("[0-9]+"))) {
                viewModelScope.launch {
                    _state.value = State.Loading
                    delay(3_000)
                    _state.value = State.Success
                    binsDao.getBin(bin)?.let {
                        val newCount = it.count + 1
                        binsDao.update(it.copy(count = newCount))
                    } ?: run { binsDao.insert(Bins(bin)) }
                }
                true
            } else false
        } else false
    }

    fun onDeleteBtn() {
        viewModelScope.launch {
            binsDao.delete()
        }
    }

    class StartViewModelFactory(private val binsDao: BinsDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StartViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StartViewModel(binsDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}