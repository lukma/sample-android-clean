package com.lukma.android.presentation.common.widget.recycler

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukma.android.R
import com.lukma.android.shared.extensions.inflate

class ProgressHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun newInstance(parent: ViewGroup) =
            ProgressHolder(parent.inflate(R.layout.item_loading))
    }
}
