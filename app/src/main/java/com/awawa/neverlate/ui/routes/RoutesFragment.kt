package com.awawa.neverlate.ui.routes


import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.view.View.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.awawa.neverlate.*
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.stops.ARGUMENT_ROUTE_ID
import com.awawa.neverlate.utils.showAddNewRouteDialog
import com.awawa.neverlate.utils.showDeleteRouteDialog
import com.awawa.neverlate.utils.toast
import kotlinx.android.synthetic.main.fragment_routes.*
import kotlinx.coroutines.*

const val ARGUMENT_TRANSPORT_ID = "transportId"

class RoutesFragment : Fragment(), RVItemClickListener {

    private lateinit var root: View
    private val adapter: RoutesAdapter = RoutesAdapter(this)

    var presenter: RoutesPresenter = RoutesPresenter(this)
    val transportId by lazy { arguments!!.getInt(ARGUMENT_TRANSPORT_ID) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_routes, container, false)
        val rvRoutes = root.findViewById<RecyclerView>(R.id.rvRoutes)
        val size = Point()
        requireActivity().windowManager.defaultDisplay.getSize(size)
        rvRoutes.layoutManager = PreCachedLayoutManager(requireContext(), size.y)
        rvRoutes.setHasFixedSize(true)
        rvRoutes.adapter = adapter
        rvRoutes.visibility = INVISIBLE
        presenter.getRoutes(transportId)

        (requireActivity() as MainActivity).registerMenuItemSelectCallback(object : MainActivity.MenuItemSelectCallback {
            override fun onItemSelected(item: MenuItem): Boolean {
                showAddNewRouteDialog()
                return true
            }
        })
        return root
    }

    suspend fun updateData(routes: List<Entities.Routes>) {
        withContext(Dispatchers.Main) {
            adapter.updateRoutes(routes)
            (activity as MainActivity).hideLoadingPanel()
            rvRoutes.visibility = VISIBLE
        }
    }

    override fun onClick(view: View) {
        val args = Bundle()
        args.putInt(ARGUMENT_ROUTE_ID, view.id)
        args.putInt(ARGUMENT_TRANSPORT_ID, transportId)
        (requireActivity() as MainActivity).showLoadingPanel()
        ((requireActivity() as MainActivity).navController).navigate(R.id.nav_stops, args)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?,
        position: Int
    ) {
        menu.add(
            0,
            v.id,
            0,
            getString(R.string.delete_route)
        )
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.order == 0) { showDeleteRouteDialog(item.itemId) }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        toast(
            requireContext(),
            "RoutesFragment action",
            Toast.LENGTH_SHORT
        )
        return true
    }
}