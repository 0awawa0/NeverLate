package com.awawa.neverlate.utils

import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.awawa.neverlate.R
import com.awawa.neverlate.db.DatabaseHelper
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.routes.RoutesFragment
import kotlinx.android.synthetic.main.layout_dialog_add_route.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


fun RoutesFragment.showDeleteRouteDialog(routeId: Int) {


        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_delete_route_title)
            .setMessage(R.string.dialog_delete_route_message)
            .setPositiveButton(android.R.string.ok) { _, _ -> run {
                this@showDeleteRouteDialog.deleteRoute(routeId)
            }}
            .setNegativeButton(android.R.string.cancel) { _, _ -> run {
                toast(requireContext(), "Dialog deletion canceled", Toast.LENGTH_SHORT)
            }}
            .show()
}


fun RoutesFragment.showAddNewRouteDialog() {
    val view = LayoutInflater.from(requireContext()).inflate(
        R.layout.layout_dialog_add_route, null, false
    )
    AlertDialog.Builder(requireContext())
        .setView(view)
        .setCancelable(false)
        .setPositiveButton(android.R.string.ok) { _, _ -> run {
            GlobalScope.launch {
                val database = DatabaseHelper.getDatabase(requireContext())
                val random = Random()
                var routeId = random.nextInt()
                while (DatabaseHelper.checkRouteId(routeId)
                    and DatabaseHelper.checkRouteId(routeId + 1)
                ) { routeId = random.nextInt() }

                if (view.etRouteNumber.text.isNotEmpty()
                    and view.etRouteFirst.text.isNotEmpty()
                    and view.etRouteLast.text.isNotEmpty()
                ) {
                    val routeNumber = view.etRouteNumber.text.toString()
                    val routeFirst = view.etRouteFirst.text.toString()
                    val routeLast = view.etRouteLast.text.toString()

                    val newRouteStraight = Entities.Routes(0,
                        "$routeFirst - $routeLast",
                        routeId,
                        transportId,
                        routeNumber,
                        0
                    )

                    val newRouteReverse = Entities.Routes(
                        0,
                        "$routeFirst - $routeLast",
                        routeId + 1,
                        transportId,
                        routeNumber,
                        1
                    )

                    database.routesDao().addNewRoute(newRouteStraight)
                    database.routesDao().addNewRoute(newRouteReverse)
                }
                withContext(Dispatchers.Main) {
                    this@showAddRouteDialog.navController.navigate(
                        navController.currentDestination!!.id
                    )
                }
            }
        }}
        .setNegativeButton(android.R.string.cancel) { _, _ -> run {
            toast(this, "Adding new route canceled", Toast.LENGTH_SHORT)
        }}
        .show()
}