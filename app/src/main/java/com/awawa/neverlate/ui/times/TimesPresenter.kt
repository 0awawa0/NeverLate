package com.awawa.neverlate.ui.times


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import com.awawa.neverlate.db.DatabaseHelper
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.utils.AlarmReceiver
import com.awawa.neverlate.utils.EXTRA_REPEAT
import com.awawa.neverlate.utils.EXTRA_TIME_ID
import com.awawa.neverlate.utils.cancelNotification
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TimesPresenter(private val view: TimesFragment) {

    private val tag = "TimesPresenter"


    fun getTimeTable(stopId: Int, weekend: Boolean) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            view.updateTimeTable(database.timesDao().getTimeTable(stopId, weekend))
        }
    }


    fun addNewTime(time: Int, night: Boolean) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            if (!DatabaseHelper.checkTime(view.stopId, time)) {
                database.timesDao().putTime(Entities.Times(
                    time,
                    view.stopId,
                    if (night) 1 else 0,
                    view.tabLayout.selectedTabPosition,
                    view.transportId
                ))

                view.updateTimeTable(database.timesDao().getTimeTable(
                    view.stopId,
                    view.tabLayout.selectedTabPosition == 1
                ))
            }
        }
    }


    fun deleteTime(timeId: Int) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            database.timesDao().deleteTime(timeId)
            view.updateTimeTable(database.timesDao().getTimeTable(
                view.stopId,
                view.tabLayout.selectedTabPosition == 1
            ))
        }
    }


    fun updateTime(time: Entities.Times) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            database.timesDao().updateTime(time)
            view.updateTimeTable(database.timesDao().getTimeTable(
                view.stopId,
                view.tabLayout.selectedTabPosition == 1
            ))
        }
    }


    fun getNotification(timeId: Int): Entities.Notifications? {
        val database = DatabaseHelper.getDatabase(view.requireContext())
        return database.notificationsDao().getByTimeId(timeId)
    }


    fun removeNotification(timeId: Int) {
        GlobalScope.launch {
            cancelNotification(view.requireContext(), timeId)
        }
    }


    fun setNotification(timeId: Int,
                        transportId: Int,
                        time: Long,
                        delta: Int,
                        daily: Boolean,
                        notification: Entities.Notifications?
    ) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            if (notification == null) {
                val timeRecord = database.timesDao().getTime(timeId)!!
                val stopRecord = database.stopsDao().getStop(timeRecord.stopId)!!
                val routeRecord = database.routesDao().getRoute(stopRecord.routeId)!!
                val newNotification = Entities.Notifications(
                    timeId,
                    transportId,
                    routeRecord.routeNumber,
                    stopRecord.stopName,
                    time,
                    delta,
                    daily
                )
                database.notificationsDao().addNotification(newNotification)
            } else {
                notification.time = time
                notification.delta = delta
                notification.repeat = daily
                database.notificationsDao().updateNotification(notification)
            }
            scheduleNotification(view.requireContext(), timeId, time, daily)
        }
    }


    private fun scheduleNotification(context: Context, timeId: Int, time: Long, repeat: Boolean) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
            .putExtra(EXTRA_REPEAT, repeat)
            .putExtra(EXTRA_TIME_ID, timeId)
            .let {
            PendingIntent.getBroadcast(context, timeId, it, 0)
        }

        val alarmManager = (
                context.getSystemService(ALARM_SERVICE) as AlarmManager
                )

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
}