package com.example.mytomatotrain.task

data class Tomato(
    val durationInMin: Int = 45,
    var status: Status = Status.CREATED
)

enum class Status {
    CREATED, STARTED, PAUSED, DONE
}