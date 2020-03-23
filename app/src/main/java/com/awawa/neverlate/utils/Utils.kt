package com.awawa.neverlate.utils


import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.awawa.neverlate.MainActivity
import com.awawa.neverlate.R
import com.awawa.neverlate.db.DatabaseHelper
import java.text.SimpleDateFormat
import java.util.*


const val NOTIFICATION_CHANNEL_ID = "neverlate_notification_channel"


fun isMarshmallowOrHigher(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M


fun timeToMinutes(time: String): Int {
    val arr = time.split(":")
    val hours = arr[0].toInt()
    val mins = arr[1].toInt()
    return (hours * 60 + mins)
}


fun minutesToTime(minutes: Int): String {
    val hours = minutes / 60
    val mins = minutes % 60
    return "%02d:%02d".format(hours, mins)
}


fun toast(context: Context, text: String, duration: Int) {
    Toast.makeText(
        context,
        text,
        duration
    ).show()
}


fun showNotification(context: Context, message: String) {
    createNotificationChannel(context)

    val notificationIntent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        notificationIntent,
        0
    )
    val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentTitle(context.getString(R.string.notification_message))
        .setContentText(message)
        .setSmallIcon(R.drawable.ic_notification)
        .setStyle(NotificationCompat.BigTextStyle()
            .bigText(message))
        .setContentIntent(pendingIntent)
        .setCategory(NotificationCompat.CATEGORY_ALARM)
        .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)
        .build()

    (context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager).notify(1, notification)
}


fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.notification_message),
            NotificationManager.IMPORTANCE_HIGH)

        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)

        (context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(notificationChannel)
    }
}


fun timeStampToTime(timeStamp: Long) : String {
    val date = Date(timeStamp)
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(date)
}

suspend fun cancelNotification(context: Context, timeId: Int) {
    val database = DatabaseHelper.getDatabase(context)

    val alarmManager = (context.getSystemService(Context.ALARM_SERVICE)) as AlarmManager

    val alarmIntent = Intent(context, AlarmReceiver::class.java)
        .let {
            PendingIntent.getBroadcast(context, timeId, it, 0)
        }

    alarmManager.cancel(alarmIntent)

    database.notificationsDao().deleteByTimeId(timeId)
}