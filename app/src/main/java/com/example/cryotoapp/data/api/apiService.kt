package com.example.cryotoapp.data.api

import com.example.cryotoapp.data.AppConstants
import com.example.cryotoapp.data.entity.ListApiResponse
import com.example.cryotoapp.data.entity.LiveApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("list?access_key=${AppConstants.Api_Key}")
    suspend fun getListData(): Response<ListApiResponse>

    @GET("live?access_key=${AppConstants.Api_Key}")
    suspend fun getLiveData(): Response<LiveApiResponse>
}