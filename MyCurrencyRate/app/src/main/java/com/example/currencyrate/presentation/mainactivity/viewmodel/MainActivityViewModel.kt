package com.example.currencyrate.presentation.mainactivity.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyrate.datasource.localdatasource.LoadedData.listDataCurrencyRate
import com.example.currencyrate.datasource.remotedatasource.data.AdapterData
import com.example.currencyrate.datasource.remotedatasource.data.Rates
import com.example.currencyrate.presentation.mainactivity.domain.MainActivityInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val interactor: MainActivityInteractor
) : ViewModel() {

    private val _adapterDataStateFlow = MutableStateFlow<List<AdapterData>?>(null)
    val adapterDataStateFlow: StateFlow<List<AdapterData>?> = _adapterDataStateFlow

    private val _successSort = MutableStateFlow(false)
    val successSort: StateFlow<Boolean> = _successSort

    private val _requestError = MutableStateFlow(false)
    val requestError: StateFlow<Boolean> = _requestError

    private val _progressBarOn = MutableStateFlow(false)
    val progressBarOn: StateFlow<Boolean> = _progressBarOn

    fun getAllCurrency(base: String) {

        val listConvertedData = mutableListOf<AdapterData>()

        viewModelScope.launch {
            try {
                _progressBarOn.value = true
                val ratesData = interactor.getAllCurrency(base)
                val listFavoriteName = interactor.getListFavorite()

                Rates::class.memberProperties.forEach { member ->
                    val name = member.name
                    val value = member.get(ratesData).toString().toDouble()
                    val isFavorite = member.name in listFavoriteName
                    listConvertedData.add(AdapterData(name, value, isFavorite))
                }

                if (listDataCurrencyRate.isEmpty()) {
                    listDataCurrencyRate = listConvertedData
                    _adapterDataStateFlow.value = listDataCurrencyRate
                    _progressBarOn.value = false
                } else {
                    val updateListDataCurrencyRate = mutableListOf<AdapterData>()

                    listDataCurrencyRate.forEach { oldData ->
                        listConvertedData.forEach { newData ->

                            if (oldData.name == newData.name) {
                                updateListDataCurrencyRate.add(oldData.copy(value = newData.value))
                            }
                        }
                    }
                    _adapterDataStateFlow.value = updateListDataCurrencyRate
                    _progressBarOn.value = false
                    listDataCurrencyRate = updateListDataCurrencyRate
                }
            } catch (e: Exception) {
                Log.e("Exception", e.message ?: "Not found apikey")
                _requestError.value = true
                _requestError.value = false
            }
        }
    }

    fun sortDataBy(comparator: Comparator<AdapterData>) {
        viewModelScope.launch {
            listDataCurrencyRate = ArrayList(listDataCurrencyRate)
            Collections.sort(listDataCurrencyRate, comparator)
            _adapterDataStateFlow.value = listDataCurrencyRate
            _successSort.value = true
            delay(600)
            _successSort.value = false
        }
    }

}