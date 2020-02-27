package com.awawa.neverlate.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


const val ACTION_NOTIFICATION = "neverlate.action.notification"

class AlarmReceiver: BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action == ACTION_NOTIFICATION) {
            showNotification(context!!)
        }
    }
}