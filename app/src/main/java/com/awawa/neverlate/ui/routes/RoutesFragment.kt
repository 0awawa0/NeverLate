package com.awawa.neverlate.ui.routes


import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.awawa.neverlate.PreCachedLayoutManager
import com.awawa.neverlate.R
import com.awawa.neverlate.db.Entities
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*


class RoutesFragment : Fragment() {

    private val adapter: RoutesAdapter = RoutesAdapter()
    private val presenter: RoutesPresenter = RoutesPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_routes, container, false)
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
            requireActivity().mainLoadingPanel.visibility = GONE
        }
    }
}