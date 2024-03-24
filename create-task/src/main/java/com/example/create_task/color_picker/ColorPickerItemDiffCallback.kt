package com.example.create_task.color_picker

import androidx.recyclerview.widget.DiffUtil
import com.example.core_api.dto.ColorItem

class ColorPickerItemDiffCallback : DiffUtil.ItemCallback<ColorItem>() {
    override fun areItemsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean =
        oldItem.colorResName == newItem.colorResName

    override fun areContentsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean =
        oldItem.isSelected == newItem.isSelected
}