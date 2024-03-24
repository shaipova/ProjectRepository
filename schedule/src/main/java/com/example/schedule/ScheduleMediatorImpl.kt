package com.example.schedule

import androidx.fragment.app.FragmentManager
import com.example.schedule_api.ScheduleMediator
import javax.inject.Inject

class ScheduleMediatorImpl @Inject constructor() : ScheduleMediator {
    override fun openScheduleScreen(containerId: Int, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(containerId, ScheduleFragment.newInstance())
            .commit()
    }
}