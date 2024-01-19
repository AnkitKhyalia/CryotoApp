package com.example.cryotoapp.util



import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@Throws(TimeoutException::class)
suspend fun <T> Flow<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    val latch = CountDownLatch(1)
    var value: T? = null

    val scope = CoroutineScope(Dispatchers.Main)
    val job = scope.launch {
        this@getOrAwaitValue.collect {
            value = it
            latch.countDown()
        }
    }

    if (!latch.await(time, timeUnit)) {
        job.cancel("Test timed out")
        throw TimeoutException("StateFlow value never changed.")
    }

    @Suppress("UNCHECKED_CAST")
    return value as T
}