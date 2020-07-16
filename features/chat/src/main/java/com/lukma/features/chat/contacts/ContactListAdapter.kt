package com.lukma.features.chat.contacts

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.lukma.core.domain.user.User

class ContactListAdapter(
    private val onSelectUser: (User) -> Unit
) : PagingDataAdapter<User, ContactViewHolder>(UserComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.newInstance(parent, onSelectUser)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        getItem(position)?.run(holder::bind)
    }
}

object UserComparator : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.email == newItem.email
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}
