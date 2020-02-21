package com.awawa.neverlate.ui.routes


import android.graphics.Point
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.awawa.neverlate.*
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.stops.ARGUMENT_ROUTE_ID
import kotlinx.android.synthetic.main.fragment_routes.*
import kotlinx.coroutines.*


class RoutesFragment : Fragment(), RVItemClickListener {

    private lateinit var root: View
    private val adapter: RoutesAdapter = RoutesAdapter(this)
    private val presenter: RoutesPresenter = RoutesPresenter(this)

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
        rvRoutes.adapter = adapter
        presenter.getRoutes((arguments!!.get("transportId") as Int))
        return root
    }

    suspend fun updateData(routes: List<Entities.Routes>) {
        withContext(Dispatchers.Main) {
            adapter.updateRoutes(routes)
            loadingPanel.visibility = GONE
        }
    }

    override fun onClick(view: View) {
        val args = Bundle()
        args.putInt(ARGUMENT_ROUTE_ID, view.id)
        ((requireActivity() as MainActivity).navController).navigate(R.id.nav_stops, args)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?,
        position: Int
    ) {
        toast(requireContext(), "onCreateContextMenu", Toast.LENGTH_SHORT)
    }
}