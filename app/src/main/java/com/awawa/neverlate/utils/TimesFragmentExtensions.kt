package com.awawa.neverlate.utils


import android.app.AlertDialog
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
    val view = layoutInflater.inflate(
        R.layout.layout_dialog_notify_time,
        null,
        false
    )

    val notification = presenter.getNotification(time._id)

    view.npTime.setOnValueChangedListener { _, _, newVal ->  run {
        val last = newVal.toString()[newVal.toString().lastIndex]
        if (last == '1' && newVal != 11) {
            view.tvMinutes.text = getString(R.string.dialog_notify_time_minutes_1)
        } else {
            if (last in arrayOf('2', '3', '4')
                && newVal !in arrayOf(12, 13, 14)
            ) {
                view.tvMinutes.text = getString(R.string.dialog_notify_time_minutes_234)
            } else {
                view.tvMinutes.text= getString(R.string.dialog_notify_time_minutes)
            }
        }
    }}

    view.npTime.maxValue = 60
    view.npTime.minValue = 0

    if (notification != null) {
        view.cbNotify.isChecked = true
        view.cbDaily.isChecked = notification.repeat
        view.npTime.value = notification.delta
    } else {
        view.cbNotify.isChecked = false
        view.npTime.value = 10
    }

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

            if (view.cbNotify.isChecked) {
                presenter.setNotification(
                    time._id,
                    transportId,
                    dueDate.timeInMillis,
                    view.npTime.value,
                    view.cbDaily.isChecked,
                    notification
                )
            } else {
                presenter.removeNotification(time._id)
            }
        }}
        .setNegativeButton(android.R.string.cancel) { _, _ -> run {

        }}
        .setCancelable(false)
        .show()
}
