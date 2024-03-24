package com.example.schedule_api

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface ScheduleMediator {

    fun openScheduleScreen(@IdRes containerId: Int, fragmentManager: FragmentManager)
}