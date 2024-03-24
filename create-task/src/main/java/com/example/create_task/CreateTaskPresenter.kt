package com.example.create_task

import android.os.Bundle
import android.view.View
import com.example.base_util.PresenterScope
import com.example.base_util.convertMinutesInEstimatedTime
import com.example.core_api.database.TasksRepository
import com.example.core_api.dto.ColorItem
import com.example.core_api.dto.Periodic
import com.example.core_api.dto.TOMATO_DURATION_IN_MIN_LARGE
import com.example.core_api.dto.Task
import com.example.core_api.dto.Tomato
import com.example.ui_core.ColorUtils.getColorItemList
import com.example.ui_core.ColorUtils.getColorResByName
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ColorPickerAdapterCallback {
    fun onColorClicked(color: String)
}

class CreateTaskPresenter @Inject constructor(
    private val repository: TasksRepository
) : ColorPickerAdapterCallback {

    private var view: CreateTaskView? = null
    private var saveState: Bundle = Bundle()

    private var name = ""
    private var tomatoesAmount = 0
    private var periodic: Periodic? = null
    private var taskColor: String = ""
    private var colorList = getColorItemList().toMutableList()
    private var job: Job? = null

    fun attachView(view: View) {
        this.view = view as CreateTaskView
    }

    fun getState(): Bundle {
        saveState.putString(TASK_NAME, name)
        saveState.putString(TASK_PERIODIC, periodic?.title)
        saveState.putString(TASK_COLOR, taskColor)
        return saveState
    }

    fun detachView() {
        if (view != null) view = null
        job?.cancel()
    }

    override fun onColorClicked(color: String) {
        val newList = changeColorListSelection(color)
        view?.updateColorPicker(newList)
        view?.updateTaskNameInputColor(getColorResByName(color))
    }

    private fun changeColorListSelection(color: String): List<ColorItem> {
        taskColor = color
        changeDoneButtonEnable()
        val list = colorList.map {
            if (it.colorResName == color) {
                it.copy(isSelected = true)
            } else {
                it.copy(isSelected = false)
            }
        }
        return list
    }

    private fun changeDoneButtonEnable() {
        val isEnabled = name.isNotBlank() && periodic != null && tomatoesAmount > 0 && taskColor.isNotBlank()
        view?.changeDoneButtonEnable(isEnabled)
    }

    private fun changeSumTomatoTaskTime() {
        val estimatedTime = tomatoesAmount * TOMATO_DURATION_IN_MIN_LARGE
        view?.changeSumTomatoTaskTime(
            convertMinutesInEstimatedTime(estimatedTime)
        )
    }

    fun setContent(state: Bundle?) {
        var colorListState = colorList

        if (state != null) {

            val nameState = state.getString(TASK_NAME) ?: ""
            val colorState = state.getString(TASK_COLOR) ?: ""
            val periodicState = state.getString(TASK_PERIODIC)?.let {
                periodTitle -> Periodic.values().find {
                    it.title == periodTitle
                }
            }

            view?.setContent(
                name = nameState,
                periodic = periodicState,
                color = colorState
            )
            colorListState = changeColorListSelection(colorState).toMutableList()
            view?.setColorAdapter(list = colorListState, callback = this)

            name = nameState
            periodic = periodicState
            taskColor = colorState
        }

        PresenterScope().launch {
            view?.observeTaskNameInput()
                ?.debounce(EDIT_TEXT_DELAY)
                ?.collect {
                    name = it
                    changeDoneButtonEnable()
                }
        }

        view?.setColorAdapter(list = colorListState, callback = this)

        view?.observeTomatoesAmountCounterChange(
            addTomatoListener = {
                tomatoesAmount++
                changeDoneButtonEnable()
                changeSumTomatoTaskTime()
            },
            removeTomatoListener = {
                tomatoesAmount--
                changeDoneButtonEnable()
                changeSumTomatoTaskTime()
            }
        )
    }

    fun setListeners(doneButtonClickListener: () -> Unit) {
        view?.setOnPeriodicButtonClickListener {
            periodic = it
            changeDoneButtonEnable()
        }
        view?.setOnDoneClick {
            job = PresenterScope().launch {
                repository.upsertTask(createTask())
                val task = repository.getTaskByName(name)
                task?.let { view?.displayTask(it) }
                doneButtonClickListener()
            }
        }
    }

    private fun createTask() = Task(
        title = name,
        listTomatoes = MutableList(tomatoesAmount) { Tomato() },
        periodic = periodic!!,
        color = taskColor
    )
}