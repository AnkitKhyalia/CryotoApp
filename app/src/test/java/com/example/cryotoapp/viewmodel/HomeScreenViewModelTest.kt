package com.example.cryotoapp.viewmodel

import android.app.Application
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.cryotoapp.data.entity.CryptoInfoResponse
import com.example.cryotoapp.repository.CryptoRepository
import com.example.cryotoapp.util.Resource
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

//import org.mockito.kotlin.whenever



@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class, sdk = [Build.VERSION_CODES.O_MR1])
class HomeScreenViewModelTest {

    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var cryptoRepository: CryptoRepository



    @Before
    fun setUp() {
         cryptoRepository = mockk<CryptoRepository>()
        // Define the behavior of getCryptoList() function
        coEvery { cryptoRepository.getCryptoList() } returns flowOf(Resource.Success(mapOf("BTC" to CryptoInfoResponse("BTC","BitCoin","BitCoin(BTC)","",""))))
        coEvery { cryptoRepository.getLiveCryptoExchangeRates() } returns flowOf(Resource.Success(mapOf("USD" to 1.0, "INR" to 73.0)))
        // Pass the mock object to your ViewModel
        viewModel = HomeScreenViewModel(cryptoRepository)



    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetCryptoList() = runBlockingTest {


        viewModel.getCryptoList()

        // Get the actual value from the LiveData
        val actualValue = viewModel.cryptoMap.value

        // Create the expected value
        val expectedValue = Resource.Success(mapOf("BTC" to CryptoInfoResponse("BTC", "BitCoin", "BitCoin(BTC)", "", "")))

        // Check if the data inside Resource.Success matches
        assertEquals(expectedValue.data, actualValue.data)
    }
    @Test
    fun testGetExchangeRates() = runBlockingTest {

        viewModel.getExchangeRates()

        // Get the actual value from the LiveData
        val actualValue = viewModel.ratesMap.value

        // Create the expected value
        val expectedValue = Resource.Success(mapOf("USD" to 1.0, "INR" to 73.0))

        // Check if the data inside Resource.Success matches
        assertEquals(expectedValue.data, actualValue.data)
    }

}