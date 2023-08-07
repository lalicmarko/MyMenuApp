package com.lalic.marko.mymenu

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.lalic.marko.mymenu.RetrofitInstance.getService
import com.lalic.marko.mymenu.api.LoginApi
import com.lalic.marko.mymenu.api.Venue
import com.lalic.marko.mymenu.ui.MyMenuApp
import com.lalic.marko.mymenu.ui.screens.LoginScreen
import com.lalic.marko.mymenu.ui.screens.VenuesScreen
import com.lalic.marko.mymenu.ui.theme.MyMenuTheme
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authPrefStore = AuthPrefStore()

        setContent {
            MyMenuTheme {
                // A surface container using the 'background' color from the theme
                MyMenuApp(
                    sharedViewModel = sharedViewModel,
                    authPrefStore = authPrefStore,
                    navigationManager = NavigationManager(),
                    networkMonitor = ConnectivityManagerNetworkMonitor(this@MainActivity)
                )
            }
        }
    }
}