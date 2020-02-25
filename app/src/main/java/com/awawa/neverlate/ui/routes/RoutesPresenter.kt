package com.awawa.neverlate.ui.routes


import com.awawa.neverlate.db.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RoutesPresenter(private val view: RoutesFragment) {

    private val tag = "RoutesPresenter"

    fun getRoutes(transportId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            view.updateData(DatabaseHelper.getDatabase(view.requireContext()).routesDao()
                .getRoutes(transportId))
        }
    }

    fun deleteRoute(routeId: Int, transportId: Int) {
        GlobalScope.launch {
            val database = DatabaseHelper.getDatabase(view.requireContext())
            val stopsForStraightRoute = database.stopsDao().getAllStops(routeId)
            val stopsForReverseRoute = database.stopsDao().getAllStops(routeId + 1)

            for (stop in stopsForStraightRoute) {
                database.timesDao().deleteTimetableForStop(stop.stopId)
            }
            database.stopsDao().deleteStopsForRoute(routeId)

            for (stop in stopsForReverseRoute) {
                database.timesDao().deleteTimetableForStop(stop.stopId)
            }
            database.stopsDao().deleteStopsForRoute(routeId + 1)

            database.routesDao().deleteRoute(routeId)
            database.routesDao().deleteRoute(routeId + 1)

            view.updateData(database.routesDao().getRoutes(transportId))
        }
    }
}