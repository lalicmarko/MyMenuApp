package com.lalic.marko.mymenu

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lalic.marko.mymenu.ui.MyMenuApp
import com.lalic.marko.mymenu.ui.screens.LOGIN_BUTTON_TEST_TAG
import com.lalic.marko.mymenu.ui.screens.LOGIN_SCREEN_TEST_TAG
import com.lalic.marko.mymenu.ui.screens.OFFLINE_ANIMATION_TEST_TAG
import com.lalic.marko.mymenu.ui.screens.VENUES_SCREEN_TEST_TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyAppNavigatesToOfflineScreenWhenInternetNotAvailable() {

        val offlineNetworkMonitor = object : NetworkMonitor {
            override val isOnline: Flow<Boolean>
                get() = flowOf(false)
        }

        val fakeNavigationManager = NavigationManager()
        val fakeAuthPrefStore = AuthPrefStore()
        val fakeViewModel = SharedViewModel()

        composeTestRule.setContent {
            MyMenuApp(
                networkMonitor = offlineNetworkMonitor,
                navigationManager = fakeNavigationManager,
                authPrefStore = fakeAuthPrefStore,
                sharedViewModel = fakeViewModel
            )
        }

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(OFFLINE_ANIMATION_TEST_TAG), 5.seconds.inWholeMilliseconds)
    }

    @Test
    fun verifyAppNavigatesToLoginScreenWhenNotAuthorizedAndInternetIsAvailable() {

        val offlineNetworkMonitor = object : NetworkMonitor {
            override val isOnline: Flow<Boolean>
                get() = flowOf(true)
        }

        val fakeNavigationManager = NavigationManager()
        val fakeAuthPrefStore = object : PrefStore {
            override fun isAuthorized(): StateFlow<Boolean> {
                return MutableStateFlow(false)
            }

            override suspend fun authorize() = Unit

        }
        val fakeViewModel = SharedViewModel()

        composeTestRule.setContent {
            MyMenuApp(
                networkMonitor = offlineNetworkMonitor,
                navigationManager = fakeNavigationManager,
                authPrefStore = fakeAuthPrefStore,
                sharedViewModel = fakeViewModel
            )
        }

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(LOGIN_SCREEN_TEST_TAG), 5.seconds.inWholeMilliseconds)
    }

    @Test
    fun verifyWhenSuccessfullyAuthorizedAppNavigatesToVenuesScreen() {

        val offlineNetworkMonitor = object : NetworkMonitor {
            override val isOnline: Flow<Boolean>
                get() = flowOf(true)
        }

        val fakeNavigationManager = NavigationManager()
        val fakeAuthPrefStore = object : PrefStore {
            val isAuthorized = MutableStateFlow(false)

            override fun isAuthorized(): StateFlow<Boolean> {
                return isAuthorized
            }

            override suspend fun authorize() = isAuthorized.emit(true)

        }
        val fakeViewModel = SharedViewModel()

        composeTestRule.setContent {
            MyMenuApp(
                networkMonitor = offlineNetworkMonitor,
                navigationManager = fakeNavigationManager,
                authPrefStore = fakeAuthPrefStore,
                sharedViewModel = fakeViewModel
            )
        }

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(LOGIN_SCREEN_TEST_TAG), 5.seconds.inWholeMilliseconds)

        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TEST_TAG).performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(VENUES_SCREEN_TEST_TAG), 5.seconds.inWholeMilliseconds)
    }
}