package com.example.mytomatotrain.timer

import android.content.Context
import com.example.mytomatotrain.utils.Constants.TIME_STORAGE

class TimerHelper(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(TIME_STORAGE, Context.MODE_PRIVATE)

    private var timerCounting = false
    private var startTime: Int? = null
    private var pauseTime: Int? = null

    fun setStartTime(time: Int) {
        /*
       будет приходить из презентера, когда нажмем на старт
       сохраняем значение в префсы
        */
    }

    fun setPauseTime(time: Int) {
        /*
       будет приходить из презентера, когда нажмем на паузу
       сохраняем значение в префсы
        */
    }

    fun setTimerCounting(isCounting: Boolean) {
        /*
       будет вызываться из презентера, когда запустили - передаем тру
        */
    }

    init {
        /*
        проверяем таймерКаунтинг - если в префсах есть значение, если нет - по дефолту фолс
        проверяем есть ли в префсах стартТайм - если есть значит
         */
    }

}