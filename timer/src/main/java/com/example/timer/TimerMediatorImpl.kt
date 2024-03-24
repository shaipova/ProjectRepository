package com.example.timer

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.timer_api.TimerMediator
import javax.inject.Inject

class TimerMediatorImpl @Inject constructor() : TimerMediator {
    override fun openTimerScreen(containerId: Int, fragmentManager: FragmentManager, bundle: Bundle) {
        val fragment = TimerFragment.newInstance()
        fragment.arguments = bundle
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(containerId, fragment)
            .commit()
    }
}