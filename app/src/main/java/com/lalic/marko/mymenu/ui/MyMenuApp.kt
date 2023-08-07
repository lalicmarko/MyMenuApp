@file:OptIn(ExperimentalFoundationApi::class)

package com.lalic.marko.mymenu.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.lalic.marko.mymenu.AuthPrefStore
import com.lalic.marko.mymenu.NavigationManager
import com.lalic.marko.mymenu.NetworkMonitor
import com.lalic.marko.mymenu.PrefStore
import com.lalic.marko.mymenu.SharedViewModel
import com.lalic.marko.mymenu.ui.screens.LoadingScreen
import com.lalic.marko.mymenu.ui.screens.LoginScreen
import com.lalic.marko.mymenu.ui.screens.OfflineScreen
import com.lalic.marko.mymenu.ui.screens.VenueDetailsScreen
import com.lalic.marko.mymenu.ui.screens.VenuesScreen
import com.lalic.marko.mymenu.ui.theme.MyMenuTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun MyMenuApp(
    networkMonitor: NetworkMonitor,
    navigationManager: NavigationManager,
    authPrefStore: PrefStore,
    appState: MyMenuAppState = rememberMyMenuAppState(
        networkMonitor = networkMonitor,
        navigationManager = navigationManager,
        authPrefStore = authPrefStore
    ),
    sharedViewModel: SharedViewModel
) {

    val currentNavRoute by appState.currentNavRoute.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))
    ) {

        when (currentNavRoute) {
            NavRoute.LOADING -> LoadingScreen(viewModel = sharedViewModel, onLoadingCompleted = {
                navigationManager.navigateTo(NavRoute.VENUES)
            })

            NavRoute.LOGIN -> LoginScreen(onAuthorized = {
                coroutineScope.launch {
                    authPrefStore.authorize()
                }
            })

            NavRoute.VENUES -> VenuesScreen(viewModel = sharedViewModel, onVenueItemClicked = {
                navigationManager.navigateTo(NavRoute.VENUE_DETAILS)
            })

            NavRoute.VENUE_DETAILS -> VenueDetailsScreen(viewModel = sharedViewModel, onBackPressed = {
                navigationManager.navigateTo(NavRoute.VENUES)
            })

            NavRoute.OFFLINE -> OfflineScreen()
            null -> {}
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MyMenuAppPreviewOnline() {
    MyMenuTheme {
        val sharedViewModel = SharedViewModel()
        val authPrefStore = AuthPrefStore()
        val networkMonitor = object : NetworkMonitor {
            override val isOnline: Flow<Boolean>
                get() = flowOf(true)
        }
        val navigationManager = NavigationManager()

        MyMenuApp(
            networkMonitor = networkMonitor,
            sharedViewModel = sharedViewModel,
            authPrefStore = authPrefStore,
            navigationManager = navigationManager
        )
    }
}
