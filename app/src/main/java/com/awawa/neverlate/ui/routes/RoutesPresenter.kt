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
}