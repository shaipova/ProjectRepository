package com.example.mytomatotrain.create_task

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.mytomatotrain.R
import com.example.mytomatotrain.utils.SimpleTextWatcher
import com.example.mytomatotrain.utils.hideSystemKeyboard
import io.reactivex.rxjava3.core.Observable

class TaskNameInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.task_name_input, this)
    }

    private val taskColor: ImageView = findViewById(R.id.title_task_color)
    private val taskNameEditText: EditText = findViewById(R.id.enter_task_name)

    fun addTaskInputListeners(listener: (Observable<String>) -> Unit) {
        taskNameEditText.setOnFocusChangeListener { view, hasFocus ->
            val name = (view as EditText).text.toString()
            if (!hasFocus) {
                taskNameEditText.hint = "New task name"
                listener(Observable.just(name))
                hideSystemKeyboard(view)
            } else {
                taskNameEditText.hint = ""
            }
        }

        taskNameEditText.addTextChangedListener(
            object : SimpleTextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    val name = s.toString()
                    listener(Observable.just(name))
                }
            }
        )
    }

    fun setTaskColor(color: Int) {
        taskColor.setColorFilter(color)
    }
}