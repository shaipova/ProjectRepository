package com.example.mytomatotrain.create_task.color_picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mytomatotrain.R
import com.example.mytomatotrain.adapters.ColorPickerItemDiffCallback
import com.example.mytomatotrain.create_task.ColorPickerAdapterCallback

class ColorPickerListAdapter(private val callback: ColorPickerAdapterCallback) : ListAdapter<ColorItem, ColorItemViewHolder>(
    ColorPickerItemDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.color_item_view, parent, false)
        return ColorItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorItemViewHolder, position: Int) {
        val colorItem = getItem(position)
        holder.bind(colorItem) {
            callback.onColorClicked(colorItem.colorResName)
        }
    }
}