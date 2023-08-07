package com.lalic.marko.mymenu

import androidx.lifecycle.ViewModel
import com.lalic.marko.mymenu.api.Venue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {

    private val venuesMutableStateFlow = MutableStateFlow<List<Venue>>(emptyList())
    val venues = venuesMutableStateFlow.asStateFlow()

    private val selectedVenueMutableStateFlow = MutableStateFlow<Venue?>(null)
    val selectedVenue = selectedVenueMutableStateFlow.asStateFlow()

    suspend fun postValues(values: List<Venue>) {
        venuesMutableStateFlow.emit(values.toMutableList())
    }

    suspend fun selectVenue(venue: Venue) {
        selectedVenueMutableStateFlow.emit(venue)
    }
}