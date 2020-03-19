package com.awawa.neverlate.utils


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import com.awawa.neverlate.R
import com.awawa.neverlate.db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


const val EXTRA_REPEAT = "REPEAT"
const val EXTRA_TIME_ID = "TIME_ID"
const val ACTION_NOTIFICATION = "neverlate.action.NOTIFICATION"

class AlarmReceiver: BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("tag", "Current time: ${Date().time}")

//        val repeat = intent!!.getBooleanExtra(EXTRA_REPEAT, false)
        val timeId = intent!!.getIntExtra(EXTRA_TIME_ID, -1);
        GlobalScope.launch(Dispatchers.IO) {
            val notification = DatabaseHelper.getDatabase(context!!).notificationsDao().getByTimeId(timeId)
            var message = ""
            message += when (notification!!.transportId) {
                TRANSPORT_ID_TRAM -> context.getString(R.string.menu_tram)
                TRANSPORT_ID_TROLLEY -> context.getString(R.string.menu_trolley)
                TRANSPORT_ID_BUS -> context.getString(R.string.menu_bus)
                TRANSPORT_ID_MARSH -> context.getString(R.string.menu_marsh)
                else -> ""
            }
            message += " № ${notification.routeNumber}"
            message += " будет на остановке \"${notification.stopName}\""
            message += " через ${notification.delta} "

            val last = notification.delta.toString()[notification.delta.toString().lastIndex]

            message += if (last == '1' && notification.delta != 11) {
                context.getString(R.string.dialog_notify_time_minutes_1)
            } else {
                if (last in arrayOf('2', '3', '4')
                    && notification.delta !in arrayOf(12, 13, 14)
                ) {
                    context.getString(R.string.dialog_notify_time_minutes_234)
                } else {
                    context.getString(R.string.dialog_notify_time_minutes)
                }
            }

            showNotification(context!!, message)
            if (!notification.repeat)
                if (timeId != -1)
                    DatabaseHelper.getDatabase(context).notificationsDao().deleteByTimeId(timeId)
            else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        (context!!.getSystemService(ALARM_SERVICE) as AlarmManager).setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            Date().time + 1000 * 60 * 60 * 24,
                            PendingIntent.getBroadcast(context, 0, intent, 0)
                        )
                    } else {
                        (context!!.getSystemService(ALARM_SERVICE) as AlarmManager).setExact(
                            AlarmManager.RTC_WAKEUP,
                            Date().time + 1000 * 60 * 60 * 24,
                            PendingIntent.getBroadcast(context, 0, intent, 0)
                        )
                    }
                }
        }
    }
}