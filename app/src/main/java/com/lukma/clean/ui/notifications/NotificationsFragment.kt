package com.lukma.clean.ui.notifications

import com.lukma.clean.R
import com.lukma.clean.ui.common.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : BaseFragment<NotificationsViewModel>() {
    override val resourceLayout = R.layout.fragment_notifications
    override val viewModel by viewModel<NotificationsViewModel>()
}
