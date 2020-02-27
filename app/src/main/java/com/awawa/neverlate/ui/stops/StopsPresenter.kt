package com.awawa.neverlate.ui.stops


import com.awawa.neverlate.db.DatabaseHelper
import com.awawa.neverlate.db.Entities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class StopsPresenter(private val view: StopsFragment) {


    fun getStops(routeId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            view.updateData(
                DatabaseHelper.getDatabase(view.requireContext()).stopsDao().getAllStops(routeId)
            )
        }
    }


    fun deleteStop(stopId: Int) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            database.timesDao().deleteTimetableForStop(stopId)
            val stops = database.stopsDao().getAllStops(
                view.routeId + view.tabLayout.selectedTabPosition
            )
            val stopToDelete = database.stopsDao().checkStop(stopId)
            for (i in stops.indices) {
                if (stops[i].stopNumber == stopToDelete!!.stopNumber) {
                    for (j in i + 1 until stops.size) {
                        stops[j].stopNumber--
                        database.stopsDao().updateStop(stops[j])
                    }
                    break
                }
            }
            database.stopsDao().deleteStop(stopId)
            view.updateData(database.stopsDao().getAllStops(
                view.routeId + view.tabLayout.selectedTabPosition
            ))
        }
    }


    fun addStop(stopName: String, stopNumber: Int) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            val random = Random()
            var stopId = random.nextInt()
            while (DatabaseHelper.checkStopId(stopId)) {
                stopId = random.nextInt()
            }

            val stops = database.stopsDao().getAllStops(
                view.routeId + view.tabLayout.selectedTabPosition
            )

            for (i in stops.indices) {
                if (stops[i].stopNumber == stopNumber) {
                    for (j in i until stops.size) {
                        stops[j].stopNumber++
                        database.stopsDao().updateStop(stops[j])
                    }
                    break
                }
            }

            val newStop = Entities.Stops(0,
                stopName,
                view.routeId + view.tabLayout.selectedTabPosition,
                stopId,
                stopNumber,
                view.transportId
            )
            database.stopsDao().addStop(newStop)

            view.updateData(database.stopsDao().getAllStops(
                view.routeId + view.tabLayout.selectedTabPosition
            ))
        }
    }
}