package com.awawa.neverlate.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*


class NotificationWorker(private val context: Context, params: WorkerParameters)
    : Worker(context, params) {

    override fun doWork(): Result {
        showNotification(context)
        return Result.success()
    }
}


fun scheduleNotification(context: Context, time: Long) {
    val alarmIntent = Intent(context, AlarmReceiver::class.java).let {
        PendingIntent.getBroadcast(context, 0, it, 0)
    }

    val alarmManager = (
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            )

    Log.e("tag", "Current time: ${Date().time} Time: $time Difference: ${time - Date().time}")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            alarmIntent
        )
    } else {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            time,
            alarmIntent
        )
    }
}