package com.example.cryotoapp.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cryotoapp.data.datasource.CryptoDataSource
import com.example.cryotoapp.data.entity.CryptoInfoResponse
import com.example.cryotoapp.data.entity.ListApiResponse
import com.example.cryotoapp.data.entity.LiveApiResponse
import com.example.cryotoapp.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CryptoRepositoryInstrumentedTest {

    private lateinit var cryptoDataSource: CryptoDataSource
    private lateinit var repository: CryptoRepository

    @Before
    fun setUp() {
        cryptoDataSource = mockk()
        repository = CryptoRepository(cryptoDataSource)
    }

    @Test
    fun getCryptoList_success() = runBlocking {
        // Mock successful response
        val response = Response.success(ListApiResponse(success = true, crypto = mapOf("BTC" to CryptoInfoResponse("BTC", "Bitcoin", "Bitcoin (BTC)", null, ""))))
        coEvery { cryptoDataSource.getListOfCurrencies() } returns response

        val flow = repository.getCryptoList()
        val result = flow.first()

        assertTrue(result is Resource.Success)
        assertEquals(mapOf("BTC" to CryptoInfoResponse("BTC", "Bitcoin", "Bitcoin (BTC)", null, "")), (result as Resource.Success).data)
    }

    @Test
    fun getCryptoList_networkError() = runBlocking {
        // Mock network error
        val error = IOException("Network error")
        coEvery { cryptoDataSource.getListOfCurrencies() } throws error

        val flow = repository.getCryptoList()
        val result = flow.first()

        assertTrue(result is Resource.Error)
        assertEquals("An error occurred", (result as Resource.Error).message)
    }

    @Test
    fun getLiveCryptoExchangeRates_success() = runBlocking {
        // Mock successful response
        val response = Response.success(LiveApiResponse(success = true, 0, "USD", rates = mapOf("BTC" to 45000.0)))
        coEvery { cryptoDataSource.getListOfLiveData() } returns response

        val flow = repository.getLiveCryptoExchangeRates()
        val result = flow.first()

        assertTrue(result is Resource.Success)
        assertEquals(mapOf("BTC" to 45000.0), (result as Resource.Success).data)
    }

    @Test
    fun getLiveCryptoExchangeRates_networkError() = runBlocking {
        // Mock network error with the correct message
        val error = IOException("Network error")
        coEvery { cryptoDataSource.getListOfLiveData() } throws error

        val flow = repository.getLiveCryptoExchangeRates()
        val result = flow.first()

        assertTrue(result is Resource.Error)
        assertEquals("An error occurred", result.message) // Extract message directly from Resource.Error
    }
}
