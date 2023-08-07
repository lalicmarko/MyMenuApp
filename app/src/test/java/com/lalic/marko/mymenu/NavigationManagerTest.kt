package com.lalic.marko.mymenu

import com.lalic.marko.mymenu.ui.NavRoute
import org.junit.Assert.assertEquals
import org.junit.Test

class NavigationManagerTest {

    @Test
    fun `when calling navigateTo with given Route then currentNavRoute is that route`() {
        val navigationManager = NavigationManager()
        assertEquals(NavRoute.LOADING, navigationManager.currentNavRoute.value)

        navigationManager.navigateTo(NavRoute.VENUE_DETAILS)
        assertEquals(NavRoute.VENUE_DETAILS, navigationManager.currentNavRoute.value)
    }
}