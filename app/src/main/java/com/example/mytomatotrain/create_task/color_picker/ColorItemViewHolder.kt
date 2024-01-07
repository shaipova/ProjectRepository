package com.example.mytomatotrain.create_task.color_picker

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytomatotrain.R
import com.example.mytomatotrain.utils.changeVisibility
import com.example.mytomatotrain.utils.setColor

class ColorItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val colorView: ImageView = itemView.findViewById(R.id.color_item)
    private val checkedIcon: ImageView = itemView.findViewById(R.id.checked_icon)

    fun setColor(color: Int) {
        colorView.setColor(color)
    }

    fun setSelected(isSelected: Boolean) {
        checkedIcon.changeVisibility(shouldShow = isSelected)
    }

    fun setOnColorClickListener(listener: () -> Unit) {
        itemView.setOnClickListener {
            listener()
        }
    }

}