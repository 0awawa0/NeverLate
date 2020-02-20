package com.awawa.neverlate.ui.times


import com.awawa.neverlate.db.DatabaseHelper
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
}