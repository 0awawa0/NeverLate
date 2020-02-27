package com.awawa.neverlate.ui.times


import com.awawa.neverlate.db.DatabaseHelper
import com.awawa.neverlate.db.Entities
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


    fun addNewTime(time: Int, night: Boolean) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            if (!DatabaseHelper.checkTime(view.stopId, time)) {
                database.timesDao().putTime(Entities.Times(
                    time,
                    view.stopId,
                    if (night) 1 else 0,
                    view.tabLayout.selectedTabPosition,
                    view.transportId
                ))

                view.updateTimeTable(database.timesDao().getTimeTable(
                    view.stopId,
                    view.tabLayout.selectedTabPosition == 1
                ))
            }
        }
    }


    fun deleteTime(timeId: Int) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            database.timesDao().deleteTime(timeId)
            view.updateTimeTable(database.timesDao().getTimeTable(
                view.stopId,
                view.tabLayout.selectedTabPosition == 1
            ))
        }
    }


    fun updateTime(time: Entities.Times) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            database.timesDao().updateTime(time)
            view.updateTimeTable(database.timesDao().getTimeTable(
                view.stopId,
                view.tabLayout.selectedTabPosition == 1
            ))
        }
    }
}