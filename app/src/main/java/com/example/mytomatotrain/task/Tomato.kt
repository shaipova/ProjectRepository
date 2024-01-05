package com.example.mytomatotrain.task

data class Tomato(
    val durationInMin: Int = TOMATO_DURATION_IN_MIN_LARGE,
    var status: Status = Status.CREATED,
    var timeLeft: Int = FULL_TOMATO_TIME_IN_SEC_LARGE
)

enum class Status {
    CREATED, STARTED, PAUSED, DONE
}

const val TOMATO_DURATION_IN_MIN_LARGE = 45
const val FULL_TOMATO_TIME_IN_SEC_LARGE = 45 * 60