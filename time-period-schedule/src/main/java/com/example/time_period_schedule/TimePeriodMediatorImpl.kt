package com.example.time_period_schedule

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.time_period_schedule_api.TimePeriodScheduleMediator
import javax.inject.Inject


class TimePeriodScheduleMediatorImpl @Inject constructor() : TimePeriodScheduleMediator {
    override fun openTimePeriodScheduleScreen(
        containerId: Int,
        fragmentManager: FragmentManager,
        bundle: Bundle
    ) {
        val fragment = TimePeriodScheduleFragment.newInstance()
        fragment.arguments = bundle
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(containerId, fragment)
            .commit()
    }
}