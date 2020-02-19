package com.awawa.neverlate.ui.routes

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.awawa.neverlate.RVItemClickListener
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.custom_views.RouteView

class RoutesAdapter(private val clickListener: RVItemClickListener)
    : RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder>() {

    private val dataSet: ArrayList<Entities.Routes> = ArrayList()

    fun updateRoutes(routes: List<Entities.Routes>) {
        dataSet.clear()
        dataSet.addAll(routes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutesViewHolder {
        val view = RouteView(parent.context, 0, "", "")
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        return RoutesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutesViewHolder, position: Int) {
        holder.view.id = dataSet[position].routeId
        holder.view.transportId = dataSet[position].transportId
        holder.view.routeName = dataSet[position].routeName
        holder.view.routeNumber = dataSet[position].routeNumber
    }

    override fun getItemCount(): Int { return dataSet.size }

    inner class RoutesViewHolder(val view: RouteView): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener { view -> run { clickListener.onClick(view) }}
        }
    }
}