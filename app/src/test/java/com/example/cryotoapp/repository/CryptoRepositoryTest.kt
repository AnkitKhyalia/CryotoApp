package com.example.cryotoapp.repository

import com.example.cryotoapp.data.datasource.CryptoDataSource
import com.example.cryotoapp.data.entity.CryptoInfoResponse
import com.example.cryotoapp.data.entity.ListApiResponse
import com.example.cryotoapp.data.entity.LiveApiResponse
import com.example.cryotoapp.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
//import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import retrofit2.HttpException
import retrofit2.Response

class CryptoRepositoryTest {

    private lateinit var cryptoDataSource: CryptoDataSource
    private lateinit var repository: CryptoRepository

    @Before
    fun setUp() {
//        mockkObject(Response)
        cryptoDataSource = mockk()
        repository = CryptoRepository(cryptoDataSource)
    }

    @Test
    fun getCryptoList_success() = runTest {
        // Mock successful response
        val response = Response.success(ListApiResponse(success = true, crypto = mapOf("BTC" to CryptoInfoResponse("BTC", "Bitcoin", "Bitcoin (BTC)", null, ""))))
        coEvery { cryptoDataSource.getListOfCurrencies() } returns response

        val flow = repository.getCryptoList()
        val result = flow.first()

        assertTrue(result is Resource.Success)
        assertEquals(mapOf("BTC" to CryptoInfoResponse("BTC", "Bitcoin", "Bitcoin (BTC)", null, "")), (result as Resource.Success).data)
    }

    @Test
    fun getCryptoList_networkError() = runTest {
        // Mock network error
        val error = HttpException(Response.error<ListApiResponse>(404, ResponseBody.create(null, "Not found")))
        coEvery { cryptoDataSource.getListOfCurrencies() } throws error

        val flow = repository.getCryptoList()
        val result = flow.first()

        assertTrue(result is Resource.Error)
        assertEquals("HTTP 404 Response.error()", (result as Resource.Error).message)
    }

    @Test
    fun getLiveCryptoExchangeRates_success() = runTest {
        // Mock successful response
        val response = Response.success(LiveApiResponse(success = true,0,"USD", rates = mapOf("BTC" to 45000.0)))
        coEvery { cryptoDataSource.getListOfLiveData() } returns response

        val flow = repository.getLiveCryptoExchangeRates()
        val result = flow.first()

        assertTrue(result is Resource.Success)
        assertEquals(mapOf("BTC" to 45000.0), (result as Resource.Success).data)
    }


    @Test
    fun getLiveCryptoExchangeRates_networkError() = runTest {
        // Mock network error with the correct message
        val error = HttpException(Response.error<LiveApiResponse>(404,
            "Not Found".toResponseBody(null)
        ))
        coEvery { cryptoDataSource.getListOfLiveData() } throws error

        val flow = repository.getLiveCryptoExchangeRates()
        val result = flow.first()

        assertTrue(result is Resource.Error)
        assertEquals("HTTP 404 Response.error()", result.message) // Extract message directly from Resource.Error
    }


}