package com.example.ui_core.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.base_util.dp

class DividerItemDecorator(private val dividerHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapterPosition = parent.getChildAdapterPosition(view)
        val adapter = parent.adapter ?: return

        if (adapter.getItemViewType(adapterPosition) == 0) {
            val nextAdapterPosition = adapterPosition + 1
            if (nextAdapterPosition < adapter.itemCount && adapter.getItemViewType(nextAdapterPosition) == 1) {
                outRect.bottom = dividerHeight.dp
            }
        }
    }
}