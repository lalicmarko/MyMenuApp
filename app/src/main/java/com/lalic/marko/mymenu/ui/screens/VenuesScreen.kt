package com.lalic.marko.mymenu.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lalic.marko.mymenu.SharedViewModel
import com.lalic.marko.mymenu.api.Venue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VenueItem(item: Venue, modifier: Modifier) {
    Card {

        Column(
            modifier = modifier
                .width(375.dp)
                .height(125.dp)
                .padding(4.dp)

        ) {
            Text(
                text = item.name, style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 23.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF2C2C31),
                )
            )
            Text(
                text = "230 m",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF2C2C31),
                )
            )
            Text(
                text = "12 Belgard Road, Tallaght, Miami",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0x992C2C31),
                )
            )
            Text(
                text = "Today 09:00 - 22:00",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0x992C2C31),
                )
            )

            if (item.isCurbside) {
                Text(
                    modifier = Modifier
                        .width(66.dp)
                        .height(18.dp)
                        .background(color = Color(0xFFF2613D), shape = RoundedCornerShape(size = 2.dp))
                        .padding(start = 4.dp, top = 1.dp, end = 4.dp, bottom = 2.dp),

                    text = "Curbside",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
        }
    }
}

const val VENUES_SCREEN_TEST_TAG = "venues-screen-test-tag"

@ExperimentalFoundationApi
@Composable
fun VenuesScreen(viewModel: SharedViewModel, onVenueItemClicked: () -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val venueItems by viewModel.venues.collectAsState()
    val venueItemsSnapshot = remember {
        venueItems.toMutableStateList()
    }


    LazyColumn(modifier = Modifier.testTag(VENUES_SCREEN_TEST_TAG), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(venueItemsSnapshot.size) { index ->
            val venueItem = venueItemsSnapshot[index]
            VenueItem(item = venueItem, modifier = Modifier.combinedClickable(
                onClick = {
                    coroutineScope.launch {
                        viewModel.selectVenue(venueItem)
                        onVenueItemClicked()
                    }
                },
                onLongClick = {
                    venueItemsSnapshot.remove(venueItem)
                }
            ))
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewVenuesScreen() {
    val viewModel = SharedViewModel()
    VenuesScreen(viewModel, onVenueItemClicked = {

    })
}