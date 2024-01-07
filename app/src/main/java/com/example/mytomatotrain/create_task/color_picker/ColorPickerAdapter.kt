package com.example.mytomatotrain.create_task.color_picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytomatotrain.R
import com.example.mytomatotrain.create_task.ColorPickerAdapterCallback
import com.example.mytomatotrain.utils.getColorResByName

class ColorPickerAdapter(private val callback: ColorPickerAdapterCallback) : RecyclerView.Adapter<ColorItemViewHolder>() {

    lateinit var colorList: List<ColorItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.color_item_view, parent, false)
        return ColorItemViewHolder(view)
    }

    override fun getItemCount(): Int = colorList.size

    override fun onBindViewHolder(holder: ColorItemViewHolder, position: Int) {
        val colorItem = colorList[position]
        holder.apply {
            setColor(getColorResByName(colorItem.colorResName))
            setSelected(colorItem.isSelected)
            setOnColorClickListener {
                callback.onColorClicked(colorItem.colorResName)
            }
        }
    }
}