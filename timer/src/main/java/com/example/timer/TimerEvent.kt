package com.example.timer

sealed class TimerEvent {
    data class TimerTickEvent(val timerValue: Int) : TimerEvent()
    data class TimerContinueEvent(val timerValue: Int?) : TimerEvent()
    data class TimerPauseEvent(val timerValue: Int?) : TimerEvent()
    data class TimerStopEvent(val timerValue: Int) : TimerEvent()
    object TimerStartEvent : TimerEvent()
    object TimerEndEvent : TimerEvent()
}