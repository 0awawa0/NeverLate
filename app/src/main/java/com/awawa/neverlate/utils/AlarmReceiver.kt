package com.awawa.neverlate.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.*


const val ACTION_NOTIFICATION = "neverlate.action.NOTIFICATION"

class AlarmReceiver: BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("tag", "Current time: ${Date().time}")
        showNotification(context!!)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (context.getSystemService(ALARM_SERVICE) as AlarmManager).setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, Date().time + 1000 * 60, PendingIntent.getBroadcast(context, 0, intent!!, 0)
            )
        } else {
            (context.getSystemService(ALARM_SERVICE) as AlarmManager).setExact(
                AlarmManager.RTC_WAKEUP, Date().time + 1000 * 60, PendingIntent.getBroadcast(context, 0, intent!!, 0)
            )
        }
    }
}