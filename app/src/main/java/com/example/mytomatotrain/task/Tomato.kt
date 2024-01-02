package com.example.mytomatotrain.task

data class Tomato(
    val durationInMin: Int = TOMATO_DURATION_IN_MIN_LARGE,
    var status: Status = Status.CREATED
)

enum class Status {
    CREATED, STARTED, PAUSED, DONE
}

const val TOMATO_DURATION_IN_MIN_LARGE = 45