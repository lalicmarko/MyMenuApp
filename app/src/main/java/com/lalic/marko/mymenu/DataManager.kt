package com.lalic.marko.mymenu

import android.content.Context
import com.lalic.marko.mymenu.api.Venue
import com.lalic.marko.mymenu.api.VenuesApi
import com.lalic.marko.mymenu.api.VenuesRequest
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class DataManager {

    suspend fun fetchVenues(
        context: Context,
        latitude: String = "44.001783",
        longitude: String = "21.26907",
        forceUpdate: Boolean = true
    ): List<Venue> {
        return if (forceUpdate) {
            try {
                RetrofitInstance.getRetrofitInstance().create(VenuesApi::class.java)
                    .getVenues(VenuesRequest(latitude, longitude)).venues
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            readVenuesFromJson(context)
        }
    }

    private suspend fun readVenuesFromJson(context: Context): List<Venue> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString = context.assets.open("venues-data.json").bufferedReader().use { it.readText() }
                val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                val listType = Types.newParameterizedType(List::class.java, Venue::class.java)
                val adapter: JsonAdapter<List<Venue>> = moshi.adapter(listType)
                adapter.fromJson(jsonString) ?: emptyList()
            } catch (ioException: IOException) {
                // Handle the exception appropriately, e.g., log an error or show a message to the user
                emptyList()
            }
        }
    }
}
