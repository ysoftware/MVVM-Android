package com.ysoftware.mvvm.array

import androidx.recyclerview.widget.RecyclerView

class ArrayViewModelUpdateHandler<VH : RecyclerView.ViewHolder, A : RecyclerView.Adapter<VH>> (val adapter: A) {

    fun handle(update:Update) {
        when (update) {
            Update.reload -> {
                adapter.notifyDataSetChanged()
            }
            Update.append -> {
                update.indexes?.let { indexes ->
                    indexes.forEach { index ->
                        adapter.notifyItemInserted(index)
                    }
                }
            }
            Update.update -> {
                update.indexes?.let { indexes ->
                    indexes.forEach { index ->
                        adapter.notifyItemChanged(index)
                    }
                }
            }
            Update.delete -> {
                update.indexes?.let { indexes ->
                    indexes.forEach { index ->
                        adapter.notifyItemRemoved(index)
                    }
                }
            }
            Update.move -> {
                update.startIndex?.let { startIndex ->
                    update.endIndex?.let { endIndex ->
                        adapter.notifyItemMoved(startIndex, endIndex)
                    }
                }
            }
        }
    }
}