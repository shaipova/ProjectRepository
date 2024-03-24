package com.example.mytomatotrain

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker.Result.Success
import androidx.work.testing.TestWorkerBuilder
import androidx.work.workDataOf
import com.example.base_util.Constants
import com.example.timer.TimerUpdateWorker
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors.newSingleThreadExecutor

@RunWith(AndroidJUnit4::class)
class TimerUpdateWorkerTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var worker: TimerUpdateWorker
    private val timerValue = 5


    @Before
    fun setUp() {
        worker = TestWorkerBuilder<TimerUpdateWorker>(
            context,
            newSingleThreadExecutor(),
            workDataOf(Constants.TIMER_VALUE to timerValue)
        ).build()
    }

    @Test
    fun testWorkerDoWork() {
        val result = worker.doWork()

        assertTrue(result is Success)
    }
}