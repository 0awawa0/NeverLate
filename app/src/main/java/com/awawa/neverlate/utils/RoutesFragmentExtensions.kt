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
            presenter.deleteRoute(routeId, transportId)
        }}
        .setNegativeButton(android.R.string.cancel) { _, _ -> run {
            toast(requireContext(), "Dialog deletion canceled", Toast.LENGTH_SHORT)
        }}
        .setCancelable(false)
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
            if (view.etRouteNumber.text.isNotEmpty()
                and view.etRouteFirst.text.isNotEmpty()
                and view.etRouteLast.text.isNotEmpty()) {
                presenter.addNewRoute(
                    view.etRouteNumber.text.toString(),
                    view.etRouteFirst.text.toString(),
                    view.etRouteLast.text.toString()
                )
            }
        }}
        .setNegativeButton(android.R.string.cancel) { _, _ -> run {
            toast(requireContext(), "Adding new route canceled", Toast.LENGTH_SHORT)
        }}
        .setCancelable(false)
        .show()
}