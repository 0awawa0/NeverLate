package com.awawa.neverlate.ui.stops


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.awawa.neverlate.MainActivity
import com.awawa.neverlate.R
import com.awawa.neverlate.db.DatabaseHelper
import com.awawa.neverlate.ui.custom_views.StopView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StopsFragment : Fragment(), TabLayout.OnTabSelectedListener {

    lateinit var root : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_stops, container, false) as LinearLayout
        val tabLayout = root.findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.addOnTabSelectedListener(this)
        loadData((activity as MainActivity).routeId, 0)
        return root
    }

    private fun loadData(routeId: Int, selectedTab: Int) = CoroutineScope(Dispatchers.Main).launch {
        val stops = DatabaseHelper.getDatabase(context!!).stopsDao().getAllStops(routeId + selectedTab)
        val llStops = root.findViewById<LinearLayout>(R.id.llStops)
        llStops.removeAllViews()
        lifecycleScope.launch(Dispatchers.Main){
            for (stop in stops) {
                val stopView = StopView(context!!, (activity as MainActivity).transportId, stop.stopName!!)
                stopView.setOnClickListener { }
                llStops.addView(stopView)
            }
            (activity as MainActivity).loadingPannel.visibility = View.GONE
        }
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {}

    override fun onTabSelected(p0: TabLayout.Tab?) {
        loadData((activity as MainActivity).routeId, p0!!.parent.selectedTabPosition)
    }
    override fun onTabUnselected(p0: TabLayout.Tab?) {}
}