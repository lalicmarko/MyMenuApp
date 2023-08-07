package com.lalic.marko.mymenu

import com.lalic.marko.mymenu.ui.NavRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationManager {

    private val currentNavRouteMutableStateFlow = MutableStateFlow(NavRoute.LOADING)
    val currentNavRoute = currentNavRouteMutableStateFlow.asStateFlow()

    fun navigateTo(navRoute: NavRoute) {
        currentNavRouteMutableStateFlow.value = navRoute
    }
}