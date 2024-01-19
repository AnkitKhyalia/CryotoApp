package com.example.cryotoapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryotoapp.data.datasource.CryptoDataSource
import com.example.cryotoapp.data.entity.CryptoInfoResponse
import com.example.cryotoapp.data.entity.ListApiResponse
import com.example.cryotoapp.data.entity.LiveApiResponse
import com.example.cryotoapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.internal.NopCollector.emit
import kotlinx.coroutines.withContext
//import java.util.concurrent.Flow
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val cryptoDataSource: CryptoDataSource
){
    // suspend function to get list of all Crypto Currencies and return them as Flow
        suspend fun getCryptoList(): Flow<Resource<Map<String, CryptoInfoResponse>>> {
        return flow {
            try {
                // response from cryptoDataSource
                val response = cryptoDataSource.getListOfCurrencies()
                if (response.isSuccessful) {
                    val cryptoList = response.body()?.crypto ?: emptyMap()
                    emit(Resource.Success(cryptoList))
                } else {
                    emit(Resource.Error("Failed to fetch cryptocurrency list"))
                }
            } catch (e: Exception) {
                throw e // Rethrow the exception for proper handling downstream
            }
        }.catch { exception -> // Use the catch operator to handle exceptions
            emit(Resource.Error(exception.message ?: "An error occurred"))
        }
     }


    // suspend function to get list of all Crypto Currencies with their respective Exchange rate return them as Flow
    suspend fun getLiveCryptoExchangeRates(): Flow<Resource<Map<String, Double>>> {
        return flow {
            try {
                val response = cryptoDataSource.getListOfLiveData()
                if (response.isSuccessful) {
                    val eachcurrencyrates = response.body()?.rates ?: emptyMap()
                    emit(Resource.Success(eachcurrencyrates))
                } else {
                    emit(Resource.Error("Failed to fetch cryptocurrency list"))
                }
            } catch (e: Exception) {
                println(e.message)
                throw e // Rethrow the exception for proper handling downstream
            }
        }.catch { exception -> // Use the catch operator to handle exceptions

            emit(Resource.Error(exception.localizedMessage ?: "An error occurred"))
        }
    }


}