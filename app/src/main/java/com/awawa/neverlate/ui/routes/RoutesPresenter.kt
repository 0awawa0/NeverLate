package com.awawa.neverlate.ui.routes


import android.util.Log
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

//    fun setAllTransportIds() {
//        GlobalScope.launch(Dispatchers.IO) {
//            val database = DatabaseHelper.getDatabase(view.requireContext())
//            val times = database.timesDao().getAllTimes()
//            for (time in times) {
//                if (time.transportId != 0) continue
//                val transportId = database.stopsDao().resolveTransportID(time.stopId)
//                if (transportId == null) {
//                    Log.e(tag, "Transport id is null for: " + time.stopId.toString())
//                    continue
//                }
//                val data1 = database.timesDao().getStops(time.stopId, false)
//                val data2 = database.timesDao().getStops(time.stopId, true)
//                if (data1.isNotEmpty())
//                    for (d in data1)
//                        d.transportId = transportId
//                database.timesDao().updateTable(data1)
//                if (data2.isNotEmpty())
//                    for (d in data2)
//                        d.transportId = transportId
//                database.timesDao().updateTable(data2)
//                Log.e(tag, time._id.toString() + " edited")
//            }
//            Log.e(tag, "Editing done")
//        }
//    }
}