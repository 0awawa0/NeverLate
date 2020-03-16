package com.awawa.neverlate.utils


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.awawa.neverlate.MainActivity
import com.awawa.neverlate.R


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


fun showNotification(context: Context) {
    createNotificationChannel(context)

    val notificationIntent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        notificationIntent,
        0
    )
    val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentText(context.getString(R.string.notification_message))
        .setSmallIcon(R.drawable.ic_notification)
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