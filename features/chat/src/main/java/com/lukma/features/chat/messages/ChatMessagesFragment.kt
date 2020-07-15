package com.lukma.features.chat.messages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.lukma.core.domain.EventState
import com.lukma.core.domain.chat.SendMessageAction
import com.lukma.core.domain.isLoading
import com.lukma.core.uikit.showSnackBar
import com.lukma.features.chat.R
import kotlinx.android.synthetic.main.chat_messages_fragment.*
import kotlinx.coroutines.launch

class ChatMessagesFragment : Fragment(R.layout.chat_messages_fragment) {
    private val viewModel: ChatMessagesViewModel by viewModels()
    private val args: ChatMessagesFragmentArgs by navArgs()

    private val listAdapter by lazy { ChatMessageListAdapter(viewModel::isCurrentUser) }

    init {
        lifecycleScope.launchWhenCreated {
            val roomId = args.roomId
            val to = args.to
            if (roomId != null) {
                viewModel.getMessages(roomId)
            } else if (to != null) {
                viewModel.getRoom(listOf(to))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageRecyclerView.adapter = listAdapter
        sendButton.setOnClickListener {
            lifecycleScope.launch {
                val message = messageInput.text?.toString()
                    ?.takeIf { !it.isBlank() }
                    ?: return@launch
                val action = viewModel.roomId
                    ?.let { SendMessageAction.Reply(it, message) }
                    ?: run {
                        val to = args.to ?: return@launch
                        SendMessageAction.New(to, message)
                    }
                viewModel.sendMessage(action)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.room.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewModel.getMessages(it.id)
            }
        }
        viewModel.messages.observe(viewLifecycleOwner) {
            listAdapter.messages = it
            messageRecyclerView.scrollToPosition(0)
        }
        viewModel.sendMessageResult.observe(viewLifecycleOwner) {
            sendButton.isEnabled = !it.isLoading
            when (it) {
                is EventState.Success -> lifecycleScope.launch {
                    messageInput.text?.clear()
                    if (viewModel.roomId == null) {
                        args.to?.let { to -> viewModel.getRoom(listOf(to)) }
                    }
                }
                is EventState.Failure -> showSnackBar(it.error)
            }
        }
    }
}
