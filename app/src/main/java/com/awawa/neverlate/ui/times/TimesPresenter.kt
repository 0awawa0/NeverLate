package com.awawa.neverlate.ui.times


import android.util.Log
import com.awawa.neverlate.db.DatabaseHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TimesPresenter(private val view: TimesFragment) {

    private val tag = "TimesPresenter"

    fun getStops(stopId: Int, weekend: Boolean) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            var data = database.timesDao().getStops(stopId, weekend)
            if (data.isNotEmpty() && data[0].transportId == 0) {
                val transportId = database.stopsDao().resolveTransportID(data[0].stopId)
                for (element in data)
                    element.transportId = transportId!!
                database.timesDao().updateTable(data)
                data = database.timesDao().getStops(stopId, weekend)
            }
            view.updateTimeTable(data)
        }
    }
}