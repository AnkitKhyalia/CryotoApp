package com.example.cryotoapp.screens.HomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cryotoapp.R
import com.example.cryotoapp.composeUtil.LoadingComposable
import com.example.cryotoapp.composeUtil.RefreshComposable
import com.example.cryotoapp.data.entity.CryptoInfoResponse
import com.example.cryotoapp.ui.theme.MoreLightGray
import com.example.cryotoapp.util.Resource
import com.example.cryotoapp.viewmodel.HomeScreenViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.StateFlow
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {

    val combinedList = homeScreenViewModel.combinedList.collectAsState().value
    val isLoading = homeScreenViewModel.swipeLoading.collectAsState()
    val isError = homeScreenViewModel.errorState.collectAsState()
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {


        when {
            isError.value != null -> {
                // Error state
                RefreshComposable(message = isError.value?:"Error") {
                    homeScreenViewModel.getCryptoList()
                }
            }
            combinedList is Resource.Loading -> {
                // Loading state
                LoadingComposable()
            }
            combinedList is Resource.Success -> {
                // Success state
                SwipeRefreshCompose(combinedList = combinedList.data?: emptyList(),isLoading,homeScreenViewModel.lastRefreshTime){
                    homeScreenViewModel.swipeDown()
                }
//                RefreshComposable(message = "This is the Test Error") {
//
//                }
            }
            combinedList is Resource.Unspecified -> {
                // Unspecified state
                Text(text = "Close the App and Retry")
            }
        }
    }

}



