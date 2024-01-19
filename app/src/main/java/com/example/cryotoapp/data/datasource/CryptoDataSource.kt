package com.example.cryotoapp.data.datasource

import com.example.cryotoapp.data.entity.ListApiResponse
import com.example.cryotoapp.data.entity.LiveApiResponse
import retrofit2.Response

interface CryptoDataSource {
    suspend fun getListOfCurrencies():Response<ListApiResponse>

    suspend fun getListOfLiveData():Response<LiveApiResponse>
}