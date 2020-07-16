package com.lukma.features.chat.contacts

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lukma.core.domain.user.User
import com.lukma.features.chat.R
import kotlinx.android.synthetic.main.contacts_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ContactsFragment : Fragment(R.layout.contacts_fragment) {
    private val viewModel: ContactsViewModel by viewModels()

    private val listAdapter by lazy { ContactListAdapter(::onSelectUser) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactRecyclerView.adapter = listAdapter
        querySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    lifecycleScope.launch {
                        viewModel.searchUsers(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    lifecycleScope.launch {
                        viewModel.searchUsers(it)
                    }
                }
                return true
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch {
            viewModel.users.collect { listAdapter.submitData(it) }
        }
    }

    private fun onSelectUser(user: User) {
        val direction = ContactsFragmentDirections.actionToChatMessagesFragment(to = user.email)
        findNavController().navigate(direction)
    }
}
