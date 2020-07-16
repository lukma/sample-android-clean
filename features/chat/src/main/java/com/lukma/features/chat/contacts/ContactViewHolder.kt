package com.lukma.features.chat.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukma.core.domain.user.User
import com.lukma.features.chat.R
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val displayNameText = itemView.displayNameText

    private lateinit var user: User
    private lateinit var onSelectUser: (User) -> Unit

    init {
        itemView.setOnClickListener {
            onSelectUser.invoke(user)
        }
    }

    fun bind(user: User) {
        this.user = user
        displayNameText.text = user.displayName
    }

    companion object {
        fun newInstance(parent: ViewGroup, onSelectUser: (User) -> Unit): ContactViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.contact_item, parent, false)
            return ContactViewHolder(view).also {
                it.onSelectUser = onSelectUser
            }
        }
    }
}
