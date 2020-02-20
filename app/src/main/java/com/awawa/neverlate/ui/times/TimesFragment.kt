package com.awawa.neverlate.ui.times


import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.awawa.neverlate.MainActivity
import com.awawa.neverlate.PreCachedLayoutManager
import com.awawa.neverlate.R
import com.awawa.neverlate.RVItemClickListener
import com.awawa.neverlate.db.Entities
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_times.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


const val ARGUMENT_STOP_ID = "stopId"

class TimesFragment: Fragment(), TabLayout.OnTabSelectedListener, RVItemClickListener {

    private val presenter = TimesPresenter(this)
    private val adapter = TimesAdapter(this)
    private val stopId by lazy { arguments!!.getInt(ARGUMENT_STOP_ID)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_times, container, false)
        val size = Point()
        requireActivity().windowManager.defaultDisplay.getSize(size)
        root.rvTimes.layoutManager = GridLayoutManager(context, 4)
        root.rvTimes.adapter = adapter
        root.tabLayout.addOnTabSelectedListener(this)
        presenter.getTimeTable(stopId, false)
        return root
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        presenter.getTimeTable(stopId, p0!!.parent.selectedTabPosition == 1)
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
    }

    override fun onClick(view: View) {
    }

    suspend fun updateTimeTable(times: List<Entities.NewTimes>) {
        withContext(Dispatchers.Main) {
            adapter.updateTimeTable(times)
            (requireActivity() as MainActivity).mainLoadingPanel.visibility = GONE
        }
    }
}