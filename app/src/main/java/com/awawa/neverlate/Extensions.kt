package com.awawa.neverlate

import android.content.Context
import android.os.Build
import android.widget.Toast

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