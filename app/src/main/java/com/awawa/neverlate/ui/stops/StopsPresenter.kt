package com.awawa.neverlate.ui.stops


import com.awawa.neverlate.db.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class StopsPresenter(private val view: StopsFragment) {

    fun getStops(routeId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            view.updateData(
                DatabaseHelper.getDatabase(view.requireContext()).stopsDao().getAllStops(routeId)
            )
        }
    }
}