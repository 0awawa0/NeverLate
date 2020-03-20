package com.awawa.neverlate.ui.settings

import com.awawa.neverlate.db.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NotificationsPresenter(val view: NotificationsFragment) {

    fun loadNotifications() {
        GlobalScope.launch(Dispatchers.IO) {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            view.updateNotificationsList(database.notificationsDao().getAllNotifications())
        }
    }
}