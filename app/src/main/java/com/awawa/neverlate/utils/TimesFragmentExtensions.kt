package com.awawa.neverlate.utils

import android.app.AlertDialog
import com.awawa.neverlate.R
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.times.TimesFragment
import kotlinx.android.synthetic.main.layout_dialog_add_time.view.*


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
    val view = layoutInflater.inflate(R.layout.layout_dialog_add_time, null, false)
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