package com.lalic.marko.mymenu

import com.lalic.marko.mymenu.ui.MyMenuAppState
import com.lalic.marko.mymenu.ui.NavRoute
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MyMenuAppStateTest {

    @Test
    fun `when authorized and online the route is given route`() = runTest(UnconfinedTestDispatcher()) {
        val authPrefStore = mockk<AuthPrefStore>().apply {
            every { isAuthorized() } returns MutableStateFlow(true)
        }
        val networkMonitor = mockk<NetworkMonitor>().apply {
            every { isOnline } returns flowOf(true)
        }
        val navigationManager = mockk<NavigationManager>().apply {
            every { currentNavRoute } returns MutableStateFlow(NavRoute.VENUES)
        }

        val appState = MyMenuAppState(this.backgroundScope, authPrefStore, networkMonitor, navigationManager)

        assertEquals(NavRoute.VENUES, appState.currentNavRoute.value)
    }

    @Test
    fun `when offline the route is Offline`() = runTest(UnconfinedTestDispatcher()) {
        val authPrefStore = mockk<AuthPrefStore>().apply {
            every { isAuthorized() } returns MutableStateFlow(false)
        }
        val networkMonitor = mockk<NetworkMonitor>().apply {
            every { isOnline } returns flowOf(false)
        }
        val navigationManager = mockk<NavigationManager>().apply {
            every { currentNavRoute } returns MutableStateFlow(NavRoute.VENUES)
        }

        val appState = MyMenuAppState(this.backgroundScope, authPrefStore, networkMonitor, navigationManager)

        assertEquals(NavRoute.OFFLINE, appState.currentNavRoute.value)
    }

    @Test
    fun `when online and unauthorized the route is Login`() = runTest(UnconfinedTestDispatcher()) {
        val authPrefStore = mockk<AuthPrefStore>().apply {
            every { isAuthorized() } returns MutableStateFlow(false)
        }
        val networkMonitor = mockk<NetworkMonitor>().apply {
            every { isOnline } returns flowOf(true)
        }
        val navigationManager = mockk<NavigationManager>().apply {
            every { currentNavRoute } returns MutableStateFlow(NavRoute.VENUES)
        }

        val appState = MyMenuAppState(this.backgroundScope, authPrefStore, networkMonitor, navigationManager)

        assertEquals(NavRoute.LOGIN, appState.currentNavRoute.value)
    }
}