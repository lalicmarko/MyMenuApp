package com.lalic.marko.mymenu.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lalic.marko.mymenu.R
import com.lalic.marko.mymenu.SharedViewModel
import com.lalic.marko.mymenu.api.Venue

@Composable
fun TextDetails(venueItem: Venue?) {
    if (venueItem == null) {
        Text(text = "An error has occurred. Venue is null.")
    } else {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .width(375.dp)
                .height(176.dp)
        ) {
            Text(
                text = venueItem.name,
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF2C2C31),
                )
            )
            Text(
                text = venueItem.welcomeMessage,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF2C2C31),
                )
            )
            Text(
                modifier = Modifier
                    .width(327.dp)
                    .height(38.dp),
                overflow = TextOverflow.Ellipsis,
                text = venueItem.description,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xCC2C2C31),
                )
            )
            Text(
                modifier = Modifier
                    .width(166.dp)
                    .height(20.dp)
                    .background(
                        color = if (venueItem.isOpen) Color.Green else Color(0xFFABABAD),
                        shape = RoundedCornerShape(size = 2.dp)
                    ),
                text = if (venueItem.isOpen) "OPEN" else "CURRENTLY CLOSED"
            )
        }
    }
}

@Composable
fun VenueDetailsScreen(viewModel: SharedViewModel, onBackPressed: () -> Unit) {
    BackHandler {
        onBackPressed()
    }

    val venueItem by viewModel.selectedVenue.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(color = Color.White)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(574.dp),
            painter = painterResource(id = R.drawable.hero_image),
            contentDescription = "details_image",
            contentScale = ContentScale.FillBounds
        )

        TextDetails(venueItem)
    }
}

@Preview
@Composable
fun PreviewVenueDetails() {
    val sharedViewModel = SharedViewModel()
    VenueDetailsScreen(viewModel = sharedViewModel, onBackPressed = {

    })
}