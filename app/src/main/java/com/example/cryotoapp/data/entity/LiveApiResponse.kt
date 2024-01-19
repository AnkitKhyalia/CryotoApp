package com.example.cryotoapp.data.entity

import com.squareup.moshi.Json

data class LiveApiResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "timestamp") val timestamp: Long,
    @Json(name = "target") val target: String,
    @Json(name = "rates") val rates: Map<String, Double>,

)