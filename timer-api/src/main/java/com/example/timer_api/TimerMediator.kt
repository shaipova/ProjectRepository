package com.example.timer_api

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface TimerMediator {
    fun openTimerScreen(@IdRes containerId: Int, fragmentManager: FragmentManager, bundle: Bundle)
}