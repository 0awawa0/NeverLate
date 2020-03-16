package com.awawa.neverlate.utils

import android.app.AlertDialog
import android.widget.Toast
import com.awawa.neverlate.R
import com.awawa.neverlate.ui.stops.StopsFragment
import kotlinx.android.synthetic.main.layout_dialog_add_stop.view.*


fun StopsFragment.showDeleteStopDialog(stopId: Int) {
    AlertDialog.Builder(requireContext())
        .setTitle(getString(R.string.dialog_delete_stop_title))
        .setMessage(getString(R.string.dialog_delete_stop_message))
        .setPositiveButton(android.R.string.ok) { _,_ -> run {
            presenter.deleteStop(stopId)
        }}
        .setNegativeButton(android.R.string.cancel) { _, _ -> run {
            toast(requireContext(), "Stop deleting canceled", Toast.LENGTH_SHORT)
        }}
        .setCancelable(false)
        .show()
}


fun StopsFragment.showAddStopDialog() {
    val view = layoutInflater.inflate(R.layout.layout_dialog_add_stop, null, false)
    AlertDialog.Builder(requireContext())
        .setView(view)
        .setPositiveButton(android.R.string.ok) { _, _ -> run {
            if (view.etStopName.text.isNotEmpty() and view.etStopOrder.text.isNotEmpty()) {
                val stopNumber = view.etStopOrder.text.toString().toInt()
                val name = view.etStopName.text.toString()
                if (stopNumber > 0) {
                    presenter.addStop(name, stopNumber)
                }
            }
        }}
        .setNegativeButton(android.R.string.cancel) { _, _ -> run {

        }}
        .setCancelable(false)
        .show()
}