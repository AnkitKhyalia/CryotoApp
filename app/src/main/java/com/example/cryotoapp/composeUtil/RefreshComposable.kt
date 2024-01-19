package com.example.cryotoapp.composeUtil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryotoapp.R

@Composable
fun RefreshComposable(
    message:String,
    onClick :()->Unit
) {
    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painter = painterResource(id = R.drawable.error_image), contentDescription ="Error Image", modifier = Modifier.padding(40.dp).size(150.dp) )
            Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Text(text = message, modifier = Modifier.padding(10.dp), color = Color.Red, fontWeight = FontWeight.Bold)
            }

            Button(onClick = onClick, modifier = Modifier
                .padding(10.dp)
                .size(140.dp, 50.dp)) {
                Text(text = "Retry", fontSize = 20.sp)
            }
        }
    }
}