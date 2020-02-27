package com.awawa.neverlate.utils


import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import com.awawa.neverlate.R
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.times.TimesFragment
import kotlinx.android.synthetic.main.layout_dialog_add_time.view.*
import kotlinx.android.synthetic.main.layout_dialog_notify_time.view.*
import java.util.*


fun TimesFragment.showAddNewTimeDialog() {
    val view = layoutInflater.inflate(R.layout.layout_dialog_add_time, null, false)
    AlertDialog.Builder(requireContext())
        .setView(view)
        .setPositiveButton(android.R.string.ok) { _, _ -> run {
            if (view.etTime.text!!.isNotEmpty()) {
                val time = timeToMinutes(view.etTime.text.toString())
                if (time < 1440)
                    presenter.addNewTime(time, view.cbNight.isChecked)
            }
        }}
        .setNegativeButton(android.R.string.cancel) { _, _ -> run {
        }}
        .setCancelable(false)
        .show()
}


fun TimesFragment.showDeleteTimeDialog(timeId: Int) {
    AlertDialog.Builder(requireContext())
        .setTitle(getString(R.string.dialog_delete_time_title))
        .setMessage(getString(R.string.dialog_delete_time_message))
        .setPositiveButton(android.R.string.ok) { _, _ -> run {
            presenter.deleteTime(timeId)
        }}
        .setNegativeButton(android.R.string.cancel) { _, _ -> run {
        }}
        .setCancelable(false)
        .show()
}


fun TimesFragment.showChangeTimeDialog(time: Entities.Times) {
    val view = layoutInflater.inflate(R.layout.layout_dialog_change_time, null, false)
    view.etTime.setText(minutesToTime(time.stopTime).replace(":", ""))
    view.cbNight.isChecked = time.night == 1
    AlertDialog.Builder(requireContext())
        .setView(view)
        .setPositiveButton(android.R.string.ok) { _, _ -> run {
            time.stopTime = timeToMinutes(view.etTime.text.toString())
            time.night = if (view.cbNight.isChecked) 1 else 0
            presenter.updateTime(time)
        }}
        .setNegativeButton(android.R.string.cancel) { _, _ -> run {
        }}
        .setCancelable(false)
        .show()
}


fun TimesFragment.showNotifyTimeDialog(time: Entities.Times) {
    val view = layoutInflater.inflate(R.layout.layout_dialog_notify_time, null, false)
    view.npTime.value = 10
    view.npTime.maxValue = 60
    view.npTime.minValue = 0

    AlertDialog.Builder(requireContext())
        .setView(view)
        .setPositiveButton(android.R.string.ok) { _, _ -> run {
            var minutes = time.stopTime - view.npTime.value
            val hours = minutes / 60
            minutes %= 60

            val currentDate = Calendar.getInstance()
            val dueDate = Calendar.getInstance()

            dueDate.set(Calendar.HOUR_OF_DAY, hours)
            dueDate.set(Calendar.MINUTE, minutes)
            dueDate.set(Calendar.SECOND, 0)

            if (dueDate.before(currentDate)) { dueDate.add(Calendar.HOUR_OF_DAY, 24) }

            val alarmIntent = Intent(requireContext(), AlarmReceiver::class.java).let {
                PendingIntent.getBroadcast(requireContext(), 0, it, 0)
            }

            val alarmManager = (
                    requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
                    )

            Log.e("tag", "Current time: ${Date().time} Time: ${dueDate.timeInMillis} Difference: ${dueDate.timeInMillis - Date().time}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    dueDate.timeInMillis,
                    alarmIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    dueDate.timeInMillis,
                    alarmIntent
                )
            }
        }}
        .setNegativeButton(android.R.string.cancel) { _, _ -> run {

        }}
        .setCancelable(false)
        .show()
}