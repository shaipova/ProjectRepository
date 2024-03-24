package com.example.base_util

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class PresenterScope : CoroutineScope {
    private val coroutineName = CoroutineName("TaskCoroutine")
    override val coroutineContext: CoroutineContext = Dispatchers.Main + coroutineName
}