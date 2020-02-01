package com.awawa.neverlate.ui.routes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.awawa.neverlate.MainActivity
import com.awawa.neverlate.R
import com.awawa.neverlate.db.DatabaseHelper
import com.awawa.neverlate.ui.custom_views.RouteView
import kotlinx.coroutines.*


class RoutesFragment : Fragment() {

    lateinit var root : ScrollView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val transportId = (activity as MainActivity).transportId
        root  = inflater.inflate(R.layout.fragment_routes, container, false) as ScrollView
        loadData(transportId)

        return root
    }

    private fun loadData(transportId: Int) = CoroutineScope(Dispatchers.Main).launch {
        val routes = DatabaseHelper.getDatabase(context!!).routesDao().getAllRoutes(transportId)
        val llRoutes = root.findViewById<LinearLayout>(R.id.llRoutes)
        lifecycleScope.launch(Dispatchers.Main){
            for (route in routes) {
                val routeView = RouteView(context!!, transportId, route.routeNumber!!, route.routeName!!)
                routeView.id = route.routeId!!
                routeView.setOnClickListener {
                    (activity as MainActivity).routeId = it.id
                    (activity as MainActivity).navController.navigate(R.id.nav_stops)
                }
                llRoutes.addView(routeView)
            }
            (activity as MainActivity).loadingPannel.visibility = View.GONE
        }
    }
}