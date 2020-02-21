package com.awawa.neverlate.ui.stops


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awawa.neverlate.RVItemClickListener
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.custom_views.StopView


class StopsAdapter(private val clickListener: RVItemClickListener)
    : RecyclerView.Adapter<StopsAdapter.StopsViewHolder>() {

    private val dataSet = ArrayList<Entities.Stops>()

    fun updateStops(data: List<Entities.Stops>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopsViewHolder {
        val view = StopView(parent.context, 0, "")
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return StopsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StopsViewHolder, position: Int) {
        holder.view.id = dataSet[position].stopId
        holder.view.stopName = dataSet[position].stopName
        holder.view.transportId = dataSet[position].transportId
    }

    override fun getItemCount(): Int { return dataSet.size }

    inner class StopsViewHolder(val view: StopView): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener{ view -> run { clickListener.onClick(view) }}
            view.setOnCreateContextMenuListener { menu, v, menuInfo -> run {
                clickListener.onCreateContextMenu(menu, v, menuInfo, adapterPosition)
            }}
        }
    }
}