package com.awawa.neverlate.ui.stops


import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.awawa.neverlate.MainActivity
import com.awawa.neverlate.PreCachedLayoutManager
import com.awawa.neverlate.R
import com.awawa.neverlate.RVItemClickListener
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.times.ARGUMENT_STOP_ID
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_stops.*
import kotlinx.android.synthetic.main.fragment_stops.view.*
import kotlinx.android.synthetic.main.layout_loading_panel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


const val ARGUMENT_ROUTE_ID = "routeId"

class StopsFragment : Fragment(), TabLayout.OnTabSelectedListener, RVItemClickListener {

    private lateinit var root: View
    private val adapter = StopsAdapter(this)
    private val presenter = StopsPresenter(this)
    private val routeId by lazy { arguments!!.getInt(ARGUMENT_ROUTE_ID) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_stops, container, false) as LinearLayout
        val size = Point()
        requireActivity().windowManager.defaultDisplay.getSize(size)
        root.rvStops.layoutManager = PreCachedLayoutManager(requireContext(), size.y)
        root.rvStops.adapter = adapter
        root.tabLayout.addOnTabSelectedListener(this)
        presenter.getStops(routeId)
        return root
    }


    override fun onTabReselected(p0: TabLayout.Tab?) {}


    override fun onTabSelected(p0: TabLayout.Tab?) {
        loadingPanel.visibility = View.VISIBLE
        presenter.getStops(routeId + p0!!.parent.selectedTabPosition)
    }


    override fun onTabUnselected(p0: TabLayout.Tab?) {}


    override fun onClick(view: View) {
        val args = Bundle()
        args.putInt(ARGUMENT_STOP_ID, view.id)
        (requireActivity() as MainActivity).navController.navigate(R.id.nav_times, args)
    }


    suspend fun updateData(stops: List<Entities.Stops>) {
        withContext(Dispatchers.Main) {
            adapter.updateStops(stops)
            loadingPanel.visibility = View.GONE
            if (stops.isEmpty())
                tvStopsError.visibility = View.VISIBLE
            else
                tvStopsError.visibility = View.GONE
        }
    }
}