package com.lalic.marko.mymenu.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lalic.marko.mymenu.R
import com.lalic.marko.mymenu.SharedViewModel

@Composable
fun LargeHeading() {
    Column(
        modifier = Modifier
            .width(375.dp)
            .height(102.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "The easiest way to order",
            style = TextStyle(
                fontSize = 28.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF2C2C31),
                textAlign = TextAlign.Center,
            )
        )
        Text(
            text = "No lines. No waiting.",
            style = TextStyle(
                fontSize = 17.sp,
                lineHeight = 23.sp,
                fontWeight = FontWeight(400),
                color = Color(0xCC2C2C31),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun LoginForm(onAuthorized: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        var usernameValue by remember {
            mutableStateOf("")
        }
        TextField(
            modifier = Modifier.width(327.dp),
            value = usernameValue, placeholder = {
                Text("Email")
            }, onValueChange = {
                usernameValue = it
            })

        Spacer(modifier = Modifier.height(30.dp))

        var passwordValue by remember {
            mutableStateOf("")
        }
        TextField(
            modifier = Modifier.width(327.dp),
            value = passwordValue, placeholder = {
                Text("Password")
            }, onValueChange = {
                passwordValue = it
            })

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF2613D)
            ),
            shape = RoundedCornerShape(size = 8.dp),
            modifier = Modifier
                .testTag(LOGIN_BUTTON_TEST_TAG)
                .width(351.dp)
                .height(56.dp),
            onClick = {
                onAuthorized()
            }
        ) {
            Text(
                text = "Sign in",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 27.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }

    }
}

const val LOGIN_SCREEN_TEST_TAG = "login-screen-test-tag"
const val LOGIN_BUTTON_TEST_TAG = "login-button-test-tag"

@Composable
fun LoginScreen(onAuthorized: () -> Unit) {
    Column(modifier = Modifier.testTag(LOGIN_SCREEN_TEST_TAG), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(172.dp))
        Image(
            painter = painterResource(id = R.drawable.splash_old),
            contentDescription = "image description",
            contentScale = ContentScale.None
        )
        Spacer(modifier = Modifier.height(72.5.dp))
        LargeHeading()
        Spacer(modifier = Modifier.height(72.5.dp))
        LoginForm(onAuthorized = onAuthorized)
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(onAuthorized = {
        
    })
}