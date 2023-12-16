package com.example.mytomatotrain.create_task

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import com.example.mytomatotrain.Navigator
import com.example.mytomatotrain.R
import com.example.mytomatotrain.db.Repository
import com.example.mytomatotrain.TaskPresenter
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.task.Tomato
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class CreateTaskPresenter(private val repository: Repository): TaskPresenter {

    private var view: CreateTaskView? = null

    private var name = ""
    private var tomatoesAmount = ""
    private var periodic: Periodic? = null

    private var navigator: Navigator? = null

    override fun attachView(view: View) {
        this.view = view as CreateTaskView
    }

    override fun detachView() {
        if (view != null) view = null
    }

    private fun changeDoneButtonEnable() {
        val isEnabled = name.isNotBlank() && periodic != null && tomatoesAmount.isNotBlank()
        view?.changeDoneButtonEnable(isEnabled)
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

        view?.observeTomatoesAmountInput {
            it
                .debounce(EDIT_TEXT_DELAY, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { text ->
                    tomatoesAmount = text
                    changeDoneButtonEnable()
                }
        }
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
                        // НАВИГИРУЕМСЯ ИЗ САМОЙ ВЬЮХИ
                    },
                    {
                        Log.i("testTag", "upsert task error: ${it.message}")
                    }
                )


        }
    }

//    override fun attachNavigator(navigator: Navigator) {
//        this.navigator = navigator
//    }

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
        listTomatoes = MutableList(tomatoesAmount.toInt()) { Tomato() },
        periodic = periodic!!,
        color = 0
    )
}