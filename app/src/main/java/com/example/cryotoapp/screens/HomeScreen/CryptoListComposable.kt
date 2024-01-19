package com.example.cryotoapp.screens.HomeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryotoapp.data.entity.CryptoInfoResponse
import com.example.cryotoapp.ui.theme.LightGreen
import com.example.cryotoapp.ui.theme.MoreLightGray
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CryptoList(combinedList:List<Pair<CryptoInfoResponse,String>>) {
    val listState = rememberLazyListState()
    LazyColumn(modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        state = listState
    ){
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)

                , contentAlignment = Alignment.Center,


            ){




                    Text(
                        text = "Feed",
                        modifier = Modifier.padding(5.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black
                    )



            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
            ){
                Divider(Modifier.padding(0.dp, 4.dp, 0.dp, 2.dp))
            }

        }
        if(combinedList.isNotEmpty()){
            items(1) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Text(text = "Swipe Down to Refresh", fontWeight = FontWeight.Normal, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                }
                Spacer(modifier = Modifier.height(10.dp))


            }
            items(count = combinedList.size,
                key = { UUID.randomUUID()},
                itemContent ={index->
                    val crypto = combinedList[index]
                    CryptoItem(name = crypto.first.fullName, image =crypto.first.iconUrl ,symbol = crypto.first.symbol, exchangeRate =crypto.second )
                }
            )
        }
    }



}