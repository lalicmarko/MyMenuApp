package com.lalic.marko.mymenu.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.lalic.marko.mymenu.AuthPrefStore
import com.lalic.marko.mymenu.NavigationManager
import com.lalic.marko.mymenu.NetworkMonitor
import com.lalic.marko.mymenu.PrefStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberMyMenuAppState(
    networkMonitor: NetworkMonitor,
    navigationManager: NavigationManager,
    authPrefStore: PrefStore,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): MyMenuAppState {
    return remember(
        coroutineScope,
        networkMonitor,
        authPrefStore
    ) {
        MyMenuAppState(
            coroutineScope,
            authPrefStore,
            networkMonitor,
            navigationManager
        )
    }
}

enum class NavRoute {
    OFFLINE, LOADING, LOGIN, VENUES, VENUE_DETAILS
}

@Stable
class MyMenuAppState(
    coroutineScope: CoroutineScope,
    authPrefStore: PrefStore,
    networkMonitor: NetworkMonitor,
    navigationManager: NavigationManager
) {

    private val isAuthorized = authPrefStore.isAuthorized()

    private val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val currentNavRoute =
        combine(isAuthorized, isOffline, navigationManager.currentNavRoute) { isAuthorized, isOffline, route ->
            if (isOffline) {
                NavRoute.OFFLINE
            } else {
                if (!isAuthorized) {
                    NavRoute.LOGIN
                } else {
                    route
                }
            }

        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}
