package com.example.cryotoapp.viewmodel

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryotoapp.data.entity.CryptoInfoResponse
import com.example.cryotoapp.repository.CryptoRepository
import com.example.cryotoapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val cryptoRepository: CryptoRepository
):ViewModel() {
    // All Crypto Currencies Map with corresponding details of icons,name,full name
    private val _cryptoMap = MutableStateFlow<Resource<Map<String,CryptoInfoResponse>>>(Resource.Unspecified())
    val cryptoMap = _cryptoMap.asStateFlow()

    // All Crypto Currencies with their Respective Exchange Rate
    private val _ratesMap =MutableStateFlow<Resource<Map<String,Double>>>(Resource.Unspecified())
    val ratesMap =_ratesMap.asStateFlow()

    // Combined list of Crypto Currencies with their Exchange Rates that will be observed by the UI
    private val _combinedList = MutableStateFlow<Resource<List<Pair<CryptoInfoResponse,String>>>>(Resource.Unspecified())
    val combinedList = _combinedList.asStateFlow()

    // Swipe Loading State
    private val _swipeLoading = MutableStateFlow(false)
    val swipeLoading = _swipeLoading.asStateFlow()

    // Periodic Refresh Time (after every 3 minutes)
    private val _refreshTime = MutableStateFlow(3 * 60_000L)

    // Last Refresh Time Flow
    private val _lastRefreshTime = MutableStateFlow(System.currentTimeMillis())
    val lastRefreshTime: StateFlow<Long> = _lastRefreshTime

    // Error State of the ViewModel
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState


    init {
        autoRefresh()
    }
    // Function to Refresh the Feed after every 3 minutes
    private fun autoRefresh(){
        viewModelScope.launch {
            while (true){
                getCryptoList()
                // Delay for 3 minutes
                delay(_refreshTime.value)

                _lastRefreshTime.value = System.currentTimeMillis()
            }

        }

    }

    // Function to get List of All Crypto Currencies list
    fun getCryptoList() {
        viewModelScope.launch {
            _combinedList.emit(Resource.Loading())
            cryptoRepository.getCryptoList()
                .flowOn(Dispatchers.IO) // Handle network calls on IO dispatcher
                .collectLatest { mapResponse ->
//                    logMapData(mapResponse.data)
                    when (mapResponse) {
                        is Resource.Success -> {
                            _cryptoMap.emit(mapResponse)

                            viewModelScope.launch {
                                getExchangeRates()
                            }
                            _errorState.value=null
                        }
                        is Resource.Error -> {
                            _cryptoMap.emit(Resource.Error(mapResponse.message?:"error in viewmodel"))
                            _errorState.value = "Error fetching Feed"
                            _combinedList.emit(Resource.Error("Unexpected error: ${mapResponse.message}"))
                        }

                        else -> {
                            _errorState.value = null
                        }
                    }
                }
        }
    }

    // Function to get Exchange Rates of all Crypto Currencies
    fun getExchangeRates(){
        viewModelScope.launch {
            Log.d("viewmodel","getexhcnagerates outside matching")
            cryptoRepository.getLiveCryptoExchangeRates()

                .collectLatest {mapResponse->

                    _ratesMap.value=mapResponse
//                    logExchangeRates(ratesMap.value.data)
                    if (_cryptoMap.value is Resource.Success && mapResponse is Resource.Success) {

                        combineCryptoWithRates(_cryptoMap.value.data, mapResponse.data)
                        _errorState.value=null
                    }
                    else{
                        _errorState.value = "Error fetching exchange rates.Please Retry"
                    }

                }
        }
    }

    // Swipe Down Event Handler Function
    fun swipeDown(){
        viewModelScope.launch {
            _swipeLoading.emit(true)
            delay(3000)
            getCryptoList()
            _lastRefreshTime.value = System.currentTimeMillis()
            _swipeLoading.emit(value = false)
        }
    }

    // Function to match Crypto Currency with Corresponding Exchange Rate
    private suspend fun combineCryptoWithRates(cryptoMap: Map<String, CryptoInfoResponse>?, ratesMap: Map<String, Double>?) {
        Log.d("viewmodel","combineCryptoWithRates")
        if (cryptoMap != null && ratesMap != null) {
            val combinedList = cryptoMap.entries.map { (symbol, cryptoInfoResponse) ->

                val rawRate = ratesMap[symbol] ?: 0.0
                val clippedRate = "%.6f".format(rawRate).take(9) // Take the first 9 characters (including the decimal point)

                val exchangeRate = if (clippedRate.contains('.')) {
                    clippedRate.removeSuffix("0").removeSuffix(".") // Remove trailing zeros and the decimal point if it's the last character
                } else {
                    clippedRate // No decimal point, no need to remove anything
                }
                cryptoInfoResponse to exchangeRate
            }
            _combinedList.emit(Resource.Success(combinedList))
            _errorState.value = null
//            _combinedList.value = Resource.Success(combinedList)
        } else {
            _combinedList.emit(Resource.Error("Crypto or rates data is null."))
//            _combinedList.value = Resource.Error("Crypto or rates data is null.")
            _errorState.value = "Error in Matching rates"
            Log.d("viewmodel","combineCryptoWithRates else block" )
        }
    }


    private fun logMapData(data: Map<String, CryptoInfoResponse>?) {
        if (data != null) {
            for ((key, value) in data) {
                Log.d("CryptoMap", "Key: $key, Value: $value")
            }
        } else {
            Log.e("CryptoMap", "Map data is null")
        }
    }

    private fun logExchangeRates(ratesMap: Map<String, Double>?) {
        if (ratesMap != null) {
            for ((key, value) in ratesMap) {
                Log.d("ExchangeRates", "Currency: $key, Exchange Rate: $value")
            }
        } else {
            Log.e("ExchangeRates", "Rates map is null")
        }
    }

}
