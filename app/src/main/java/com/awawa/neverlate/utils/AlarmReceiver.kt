package com.awawa.neverlate.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


const val ACTION_NOTIFICATION = "neverlate.action.NOTIFICATION"

class AlarmReceiver: BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("tag", "Received intent")
        showNotification(context!!)
    }
}