package com.example.mytomatotrain.create_task

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import com.example.mytomatotrain.db.Repository
import com.example.mytomatotrain.TaskPresenter
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.task.TOMATO_DURATION_IN_MIN_LARGE
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.task.Tomato
import com.example.mytomatotrain.utils.convertMinutesInEstimatedTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class CreateTaskPresenter(private val repository: Repository) : TaskPresenter {

    private var view: CreateTaskView? = null

    private var name = ""
    private var tomatoesAmount = 0
    private var periodic: Periodic? = null

    override fun attachView(view: View) {
        this.view = view as CreateTaskView
    }

    override fun detachView() {
        if (view != null) view = null
    }

    private fun changeDoneButtonEnable() {
        val isEnabled = name.isNotBlank() && periodic != null && tomatoesAmount > 0
        view?.changeDoneButtonEnable(isEnabled)
    }

    private fun changeSumTomatoTaskTime() {
        val estimatedTime = tomatoesAmount * TOMATO_DURATION_IN_MIN_LARGE
        view?.changeSumTomatoTaskTime(
            convertMinutesInEstimatedTime(estimatedTime)
        )
    }

    @SuppressLint("CheckResult")
    override fun setContent() {
        view?.observeTaskNameInput {
            it
                .debounce(EDIT_TEXT_DELAY, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { text ->
                    name = text
                    changeDoneButtonEnable()
                }
        }

        view?.observeTomatoesAmountCounterChange(
            addTomatoListener = { completable ->
                completable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        tomatoesAmount++
                        changeDoneButtonEnable()
                        changeSumTomatoTaskTime()
                    }
            },
            removeTomatoListener = { completable ->
                completable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        tomatoesAmount--
                        changeDoneButtonEnable()
                        changeSumTomatoTaskTime()
                    }
            }
        )
    }

    @SuppressLint("CheckResult")
    override fun setListeners() {
        view?.setOnPeriodicButtonClickListener {
            periodic = it
            changeDoneButtonEnable()
        }
        view?.setOnDoneClick {
            repository.upsertTask(createTask())
                .subscribe(
                    {
                        displayTasks(name)
                        // НАВИГИРУЕМСЯ ИЗ САМОЙ ВЬЮХИ, не отсюда
                    },
                    {
                        Log.i("testTag", "upsert task error: ${it.message}")
                    }
                )


        }
    }

    private fun displayTasks(s: String) {
        repository.getTaskByName(s)
            .subscribe(
                {
                    view?.displayTasks(listOf(it))
                },
                {
                    Log.i("testTag", "displayTasks error: ${it.message}")
                }
            )
    }

    private fun createTask() = Task(
        title = name,
        listTomatoes = MutableList(tomatoesAmount) { Tomato() },
        periodic = periodic!!,
        color = 0
    )
}