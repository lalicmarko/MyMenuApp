package com.lalic.marko.mymenu

import kotlinx.coroutines.flow.StateFlow

interface PrefStore {

    fun isAuthorized(): StateFlow<Boolean>
    suspend fun authorize()
}