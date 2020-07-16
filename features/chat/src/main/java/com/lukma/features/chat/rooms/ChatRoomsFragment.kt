package com.lukma.features.chat.rooms

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.lukma.features.chat.R
import kotlinx.android.synthetic.main.chat_rooms_fragment.*

class ChatRoomsFragment : Fragment(R.layout.chat_rooms_fragment) {
    private val viewModel: ChatRoomsViewModel by viewModels()

    private val listAdapter by lazy { ChatRoomListAdapter(::onOpenMessages) }

    init {
        lifecycleScope.launchWhenCreated {
            viewModel.getRooms()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomRecyclerView.adapter = listAdapter
        composeButton.setOnClickListener {
            val direction = ChatRoomsFragmentDirections.actionToContactsFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.rooms.observe(viewLifecycleOwner) {
            listAdapter.rooms = it
        }
    }

    private fun onOpenMessages(roomId: String) {
        val direction = ChatRoomsFragmentDirections.actionToChatMessagesFragment(roomId = roomId)
        findNavController().navigate(direction)
    }
}
