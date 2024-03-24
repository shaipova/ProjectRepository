package com.example.create_task

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.base_util.SimpleTextWatcher
import com.example.base_util.hideSystemKeyboard
import com.example.base_util.setColor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

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

    fun addTaskInputListeners() = callbackFlow {
        taskNameEditText.setOnFocusChangeListener { view, hasFocus ->
            val name = (view as EditText).text.toString()
            if (!hasFocus) {
                taskNameEditText.hint = "New task name"
                trySend(name)
                hideSystemKeyboard(view)
            } else {
                taskNameEditText.hint = ""
            }
        }

        val textWatcher = object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val name = s.toString()
                trySend(name)
            }
        }

        taskNameEditText.addTextChangedListener(textWatcher)

        awaitClose {
            taskNameEditText.onFocusChangeListener = null
            taskNameEditText.removeTextChangedListener(textWatcher)
        }
    }

    fun setText(text: String) {
        taskNameEditText.setText(text)
    }

    fun setTaskColor(color: Int) {
        taskColor.setColor(color)
    }
}