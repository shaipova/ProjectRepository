package com.example.main_module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core_api.AppWithFacade
import com.example.schedule_api.ScheduleMediator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var scheduleMediator: ScheduleMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainComponent.create((application as AppWithFacade).getFacade()).inject(this)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            scheduleMediator.openScheduleScreen(R.id.main_fragments_container, supportFragmentManager)
        }
    }
}