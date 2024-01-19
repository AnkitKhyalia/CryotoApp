package com.example.cryotoapp.data.datasource

import com.example.cryotoapp.data.api.ApiService
import com.example.cryotoapp.data.entity.ListApiResponse
import com.example.cryotoapp.data.entity.LiveApiResponse
import retrofit2.Response
import javax.inject.Inject

class CryptoDataSourceImpl @Inject constructor(
    private val apiService: ApiService
):CryptoDataSource {
    override suspend fun getListOfCurrencies(): Response<ListApiResponse> {
        return apiService.getListData()
    }

    override suspend fun getListOfLiveData(): Response<LiveApiResponse> {
        return apiService.getLiveData()
    }

}