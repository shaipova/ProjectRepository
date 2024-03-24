package com.example.timer

import android.util.Log
import android.view.View
import com.example.base_util.PresenterScope
import com.example.base_util.convertSecondsInTimerFormat
import com.example.core_api.database.QuotesRepository
import com.example.core_api.dto.FULL_TOMATO_TIME_IN_SEC_LARGE
import com.example.core_api.dto.Task
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class TimerPresenter @Inject constructor(
    private val quotesRepository: QuotesRepository
) {

    private var view: TimerView? = null
    private var timerEventListener: TimerEventListener? = null
    private var lastTimerValue: Int? = null
    private var job: Job? = null
    private lateinit var timerHelper: TimerHelper

    fun attachView(view: View) {
        this.view = view as TimerView

        job = PresenterScope().launch {
            try {
                val quote = quotesRepository.getQuote().random()
                view.setQuote(quote)
            } catch (e: Exception) {
                if (e is CancellationException) {
                    Log.e("Error", "QuotesRepository error. Coroutine cancelled: ${e.message}")
                    throw e
                }
                if (e is SocketTimeoutException) {
                    Log.e("Error", "QuotesRepository error. SocketTimeoutException: ${e.message}")
                }
            } catch (e: Throwable) {
                Log.e("Error", "QuotesRepository error. Error message: ${e.message}")
            }
        }
    }

    fun setContent(task : Task?) {
        view?.setTask(task)
    }

    fun setHelper(timerHelper: TimerHelper) {
        this.timerHelper = timerHelper
    }

    fun setTimerEventListener(eventListener: TimerEventListener) {
        this.timerEventListener = eventListener
    }

    fun startTimer() {
        view?.startTimer()
    }

    fun updateTimer(timerValue: Int) {
        val timeFormat = convertSecondsInTimerFormat(timerValue)
        view?.updateTimer(timeFormat)
        lastTimerValue = timerValue
    }

    fun stopTimerAnimation() {
        view?.stopAnimation()
    }

    fun endTimer() {
        view?.endTimer()
    }

    fun setListeners() {
        view?.setPauseTimerButtonClickListener {
            timerEventListener?.onTimerEvent(TimerEvent.TimerPauseEvent(lastTimerValue))
            view?.pauseTimer()
        }
        view?.setStartTimerButtonClickListener {
            timerEventListener?.onTimerEvent(TimerEvent.TimerStartEvent)
        }
        view?.setContinueTimerButtonClickListener {
            timerEventListener?.onTimerEvent(TimerEvent.TimerContinueEvent(lastTimerValue))
        }
        view?.setStopTimerButtonClickListener {
            timerEventListener?.onTimerEvent(TimerEvent.TimerStopEvent(FULL_TOMATO_TIME_IN_SEC_LARGE))
            view?.stopTimer()
        }
    }

    fun detachView() {
        if (view != null) view = null
        job?.cancel()
    }
}