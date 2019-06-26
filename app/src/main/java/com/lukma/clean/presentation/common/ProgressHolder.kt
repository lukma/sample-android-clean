package com.lukma.clean.presentation.common

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukma.clean.R
import com.lukma.clean.shared.extensions.inflate

class ProgressHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun newInstance(parent: ViewGroup) = ProgressHolder(parent.inflate(R.layout.item_loading))
    }
}
