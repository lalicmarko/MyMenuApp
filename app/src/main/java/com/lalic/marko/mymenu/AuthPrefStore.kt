package com.lalic.marko.mymenu

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthPrefStore: PrefStore {

    private val authorized = MutableStateFlow(false)

    override fun isAuthorized(): StateFlow<Boolean> = authorized.asStateFlow()

    override suspend fun authorize() {
        authorized.emit(true)
    }
}