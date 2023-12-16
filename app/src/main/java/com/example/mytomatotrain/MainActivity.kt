package com.example.mytomatotrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.example.mytomatotrain.create_task.CreateTaskFragment
import com.example.mytomatotrain.create_task.CreateTaskPresenter
import com.example.mytomatotrain.db.Repository
import com.example.mytomatotrain.db.TasksDatabase
import com.example.mytomatotrain.scheduler.ScheduleFragment
import com.example.mytomatotrain.scheduler.SchedulePresenter
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.time_period.PeriodicFragment
import com.example.mytomatotrain.time_period.PeriodicPresenter
import com.example.mytomatotrain.timer.TimerFragment
import com.example.mytomatotrain.timer.TimerPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.main_toolbar))

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}