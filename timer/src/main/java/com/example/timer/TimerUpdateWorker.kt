package com.example.timer

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.base_util.Constants.TIMER_VALUE

class TimerUpdateWorker(
    context: Context,
    parameters: WorkerParameters
) : Worker(context, parameters) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private var listener: TimerEventListener? = null

        fun setListener(listener: TimerEventListener) {
            this.listener = listener
        }
    }

    override fun doWork(): Result {
        return try {
            val timerValue = inputData.getInt(TIMER_VALUE, 25*60)
            for (i in timerValue downTo 0) {
                if (isStopped) {
                    Result.success()
                } else {
                    handler.post {
                        listener?.onTimerEvent(TimerEvent.TimerTickEvent(i))
                    }
                    Thread.sleep(1000)
                }
            }
            handler.post {
                if (!isStopped) {
                    listener?.onTimerEvent(TimerEvent.TimerEndEvent)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
