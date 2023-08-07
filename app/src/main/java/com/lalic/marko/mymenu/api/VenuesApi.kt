package com.lalic.marko.mymenu.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface VenuesApi {
    @Headers(
        "application: mobile-application",
        "Content-Type: application/json",
        "Device-UUID: 123456",
        "Api-Version: 3.7.0"
    )
    @POST("api/directory/search")
    suspend fun getVenues(@Body venuesRequest: VenuesRequest): VenuesResponse
}

data class VenuesRequest(val latitude: String, val longitude: String)

data class VenuesResponse(val venues: List<Venue>)

data class Venue(
    var name: String,
    val description: String,
    @SerializedName("is_open") val isOpen: Boolean,
    @SerializedName("welcome_message") val welcomeMessage: String,
    val images: Images,
    val isCurbside: Boolean
)

data class Images(@SerializedName("thumbnail_medium") val thumbnailMedium: String)

