package com.example.cryotoapp.screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cryotoapp.R
import com.example.cryotoapp.ui.theme.DarkGreen

@Composable
fun CryptoItem(
    name:String,
    image:String,
    symbol:String,
    exchangeRate:String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(5.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
//            Image(imageVector = Icons.Default.Notifications, contentDescription ="Currency logo", modifier = Modifier.size(50.dp) )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_kleine_shape),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(80.dp)
                    .padding(5.dp)
                    .clip(shape = RoundedCornerShape(10.dp)).background(Color.Transparent),

                )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                    Text(text = name, modifier = Modifier.padding(5.dp), fontWeight = FontWeight.Bold, color = Color.Black)
                    Text(text = symbol, modifier = Modifier.padding(5.dp), fontWeight = FontWeight.Light, color = Color.Gray, fontSize = 10.sp)
                }
//                Text(text = name, modifier = Modifier.padding(5.dp), fontWeight = FontWeight.Bold)
                Text(text = "Exchange Rate: $$exchangeRate", modifier = Modifier.padding(5.dp),color= DarkGreen, style = TextStyle(
                    fontSize = 15.sp
                ))
            }



        }

    }
}
