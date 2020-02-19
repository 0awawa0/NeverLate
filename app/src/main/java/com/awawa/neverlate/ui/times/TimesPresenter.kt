package com.awawa.neverlate.ui.times

import com.awawa.neverlate.db.DatabaseHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TimesPresenter(private val view: TimesFragment) {


    fun getStops(stopId: Int, weekend: Boolean) {
        GlobalScope.launch {
            view.updateTimeTable(
                DatabaseHelper.getDatabase(view.requireContext())
                    .timesDao()
                    .getStops(stopId, weekend)
            )
        }
    }
}