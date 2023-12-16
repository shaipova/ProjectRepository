package com.example.mytomatotrain.timer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mytomatotrain.utils.Constants.CHANNEL_ID
import com.example.mytomatotrain.utils.Constants.NOTIFICATION_ID
import com.example.mytomatotrain.utils.Constants.TIMER_CHANNEL
import com.example.mytomatotrain.utils.Constants.TIMER_VALUE

class TimerUpdateWorker(val context: Context, parameters: WorkerParameters) : Worker(context, parameters) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private var listener: TimerEventListener? = null

        fun setListener(listener: TimerEventListener) {
            this.listener = listener
        }
    }

    override fun doWork(): Result {
        //val updatedNotification = createNotification(updatedTimerValue)
        //val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //notificationManager.notify(NOTIFICATION_ID, updatedNotification)

        val timerValue = inputData.getInt(TIMER_VALUE, 25*60)

        for (i in timerValue downTo 0) {
            handler.post {
                listener?.onTimerEvent(TimerEvent.TimerCountingEvent(i))
            }
            Thread.sleep(1000)
        }
        return Result.success()
    }

    // создаем уведомление
    private fun createNotification(timerValue: Int): Notification {
        // Создание канала уведомления (для Android 8 и выше)
        createNotificationChannel()

        //val notificationIntent = Intent(context, TimerActivity::class.java)
        //val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Таймер")
            .setContentText("$timerValue сек.")
            //.setSmallIcon(R.drawable.ic_notification)
            //.setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = TIMER_CHANNEL
            val descriptionText = "Канал для уведомлений о таймере"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


}

sealed class TimerEvent {
    data class TimerCountingEvent(val timerValue: Int) : TimerEvent()
    data class TimerPauseEvent(val timerValue: Int) : TimerEvent()

}

