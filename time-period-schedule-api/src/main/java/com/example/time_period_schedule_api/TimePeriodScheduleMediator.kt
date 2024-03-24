package com.example.time_period_schedule_api

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface TimePeriodScheduleMediator {
    fun openTimePeriodScheduleScreen(@IdRes containerId: Int, fragmentManager: FragmentManager, bundle: Bundle)
}