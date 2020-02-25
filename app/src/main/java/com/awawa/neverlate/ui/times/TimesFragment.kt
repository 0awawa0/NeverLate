package com.awawa.neverlate.ui.times


import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.awawa.neverlate.MainActivity
import com.awawa.neverlate.R
import com.awawa.neverlate.RVItemClickListener
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.routes.ARGUMENT_TRANSPORT_ID
import com.awawa.neverlate.utils.showAddNewTimeDialog
import com.awawa.neverlate.utils.showChangeTimeDialog
import com.awawa.neverlate.utils.showDeleteTimeDialog
import com.awawa.neverlate.utils.toast
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_times.*
import kotlinx.android.synthetic.main.fragment_times.view.*
import kotlinx.android.synthetic.main.fragment_times.view.tabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


const val ARGUMENT_STOP_ID = "stopId"

class TimesFragment: Fragment(), TabLayout.OnTabSelectedListener, RVItemClickListener {

    private lateinit var root: View
    private val adapter = TimesAdapter(this)

    val presenter = TimesPresenter(this)

    val stopId by lazy { arguments!!.getInt(ARGUMENT_STOP_ID)}
    val trasnportId by lazy { arguments!!.getInt(ARGUMENT_TRANSPORT_ID)}
    val tabLayout: TabLayout by lazy { root.findViewById<TabLayout>(R.id.tabLayout) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_times, container, false)
        val size = Point()
        requireActivity().windowManager.defaultDisplay.getSize(size)
        root.rvTimes.layoutManager = GridLayoutManager(context, 4)
        root.rvTimes.adapter = adapter
        root.tabLayout.addOnTabSelectedListener(this)
        presenter.getTimeTable(stopId, false)

        (requireActivity() as MainActivity).registerMenuItemSelectCallback(object: MainActivity.MenuItemSelectCallback {
            override fun onItemSelected(item: MenuItem): Boolean {
                showAddNewTimeDialog()
                return true
            }
        })
        return root
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {}

    override fun onTabSelected(p0: TabLayout.Tab?) {
        loadingPanel.visibility = VISIBLE
        presenter.getTimeTable(stopId, p0!!.parent!!.selectedTabPosition == 1)
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {}

    override fun onClick(view: View) {
        toast(requireContext(), "Click", Toast.LENGTH_SHORT)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?,
        position: Int
    ) {
        menu.add(
            2,
            v.id,
            0,
            getString(R.string.delete)
        )
        menu.add(
            2,
            v.id,
            1,
            getString(R.string.change)
        )
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.order == 0) showDeleteTimeDialog(item.itemId)
        if (item.order == 1) {
            val time = adapter.getItemById(item.itemId)
            if (time != null)
                showChangeTimeDialog(time)
        }
        return super.onContextItemSelected(item)
    }

    suspend fun updateTimeTable(times: List<Entities.Times>) {
        withContext(Dispatchers.Main) {
            adapter.updateTimeTable(times)
            loadingPanel.visibility = GONE
            if (times.isEmpty()) tvTimesError.visibility = VISIBLE
            else tvTimesError.visibility = GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        toast(
            requireContext(),
            "TimesFragment action",
            Toast.LENGTH_SHORT
        )
        return true
    }
}