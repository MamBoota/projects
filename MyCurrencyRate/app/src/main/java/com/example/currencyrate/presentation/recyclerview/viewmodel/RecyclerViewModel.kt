package com.example.currencyrate.presentation.recyclerview.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyrate.datasource.localdatasource.LoadedData.listDataCurrencyRate
import com.example.currencyrate.datasource.remotedatasource.data.AdapterData
import com.example.currencyrate.db.entity.Favorite
import com.example.currencyrate.presentation.recyclerview.domain.RecyclerInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecyclerViewModel @Inject constructor(
    private val interactor: RecyclerInteractor
) : ViewModel() {

    private var _popularStateFlow = MutableStateFlow<List<AdapterData>?>(null)
    val popularStateFlow: StateFlow<List<AdapterData>?> = _popularStateFlow

    private var _favoriteStateFlow = MutableStateFlow<List<AdapterData>?>(null)
    val favoriteStateFlow: StateFlow<List<AdapterData>?> = _favoriteStateFlow

    fun loadingData() {
        viewModelScope.launch {
            try {
                _favoriteStateFlow.value = listDataCurrencyRate.filter { it.isFavorite }
            } catch (e: Exception) {
                Log.e("Exception", e.message ?: "qwerty")
            }
        }
    }


    fun favoriteExists(data: AdapterData) {
        viewModelScope.launch {
            try {
                val index = listDataCurrencyRate.indexOfFirst { it.name == data.name }
                val updateData =
                    listDataCurrencyRate[index].copy(isFavorite = !listDataCurrencyRate[index].isFavorite)

                if (interactor.favoriteExists(data.name)) {
                    interactor.deleteFavorite(Favorite(data.name))
                } else {
                    interactor.insertFavorite(Favorite(data.name))
                }

                listDataCurrencyRate = ArrayList(listDataCurrencyRate)
                listDataCurrencyRate[index] = updateData
                _popularStateFlow.value = listDataCurrencyRate
                loadingData()

            } catch (e: Exception) {
                Log.e("Exception", e.message ?: "qwerty")
            }
        }
    }
}