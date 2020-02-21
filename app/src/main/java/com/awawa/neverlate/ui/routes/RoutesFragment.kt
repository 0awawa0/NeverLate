package com.awawa.neverlate.ui.routes


import android.app.AlertDialog
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.view.View.GONE
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
        menu.add(
            0,
            v.id,
            0,
            getString(R.string.add_route)
        )
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.order == 0) {
            val view = LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_dialog_add_route, null, false
            )
            AlertDialog.Builder(requireContext())
                .setView(view)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok) { _, _ -> run {
                    toast(requireContext(), "Added route", Toast.LENGTH_SHORT)
                }}
                .setNegativeButton(android.R.string.cancel) { _, _ -> run {
                    toast(requireContext(), "Adding new route canceled", Toast.LENGTH_SHORT)
                }}
                .show()
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        toast(requireContext(), "RoutesFragment action", Toast.LENGTH_SHORT)
        return true
    }
}