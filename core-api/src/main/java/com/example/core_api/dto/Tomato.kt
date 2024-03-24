package com.example.core_api.dto

data class Tomato(
    val durationInMin: Int = TOMATO_DURATION_IN_MIN_LARGE,
    var status: Status = Status.CREATED,
    var timeLeft: Int = FULL_TOMATO_TIME_IN_SEC_LARGE
)

enum class Status {
    CREATED, STARTED, PAUSED, DONE
}

const val TOMATO_DURATION_IN_MIN_LARGE = 25
const val FULL_TOMATO_TIME_IN_SEC_LARGE = TOMATO_DURATION_IN_MIN_LARGE * 60