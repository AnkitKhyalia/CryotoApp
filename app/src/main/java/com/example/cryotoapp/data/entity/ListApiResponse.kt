package com.example.cryotoapp.data.entity

import com.squareup.moshi.Json

data class ListApiResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "crypto") val crypto: Map<String, CryptoInfoResponse>,
    // Add other properties if needed
)

data class CryptoInfoResponse(
    @Json(name = "symbol") val symbol: String,
    @Json(name = "name") val name: String,
    @Json(name = "name_full") val fullName: String,
    @Json(name = "max_supply") val maxSupply: Any?,
    @Json(name = "icon_url") val iconUrl: String
)

