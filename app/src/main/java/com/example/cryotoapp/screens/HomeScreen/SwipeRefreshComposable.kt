package com.example.cryotoapp.screens.HomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cryotoapp.data.entity.CryptoInfoResponse
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.StateFlow
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SwipeRefreshCompose(
    combinedList: List<Pair<CryptoInfoResponse, String>>,
    isLoading: State<Boolean>,
    lastReloadTime: StateFlow<Long>,
    onClick: () -> Unit
) {

    val swipeRefreshRate = rememberSwipeRefreshState(isRefreshing = isLoading.value)

    SwipeRefresh(
        state = swipeRefreshRate,
        onRefresh = {
            onClick.invoke()
        },
    ) {

        // list view
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(0.95f)){

                CryptoList(combinedList = combinedList)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(0.05f).clip(RoundedCornerShape(20.dp)).background(color = Color.LightGray),
                contentAlignment = Alignment.Center
            ){

                val formatter: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)

                val text: String = formatter.format(Date(lastReloadTime.value))

                Text(text = "Last Reload At: $text", color = Color.Gray)
            }

        }



    }

}