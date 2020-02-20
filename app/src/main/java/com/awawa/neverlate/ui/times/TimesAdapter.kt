package com.awawa.neverlate.ui.times


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awawa.neverlate.RVItemClickListener
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.custom_views.TimeView
import java.sql.Time


class TimesAdapter(private val clickListener: RVItemClickListener)
    : RecyclerView.Adapter<TimesAdapter.TimesViewHolder>() {

    private val dataSet = ArrayList<Entities.Times>()

    fun updateTimeTable(times: List<Entities.Times>) {
        dataSet.clear()
        dataSet.addAll(times)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimesViewHolder {
        val view = TimeView(parent.context, "", 0, 0, 0)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return TimesViewHolder(view)
    }


    override fun onBindViewHolder(holder: TimesViewHolder, position: Int) {
        holder.view.id = dataSet[position]._id
        holder.view.time = dataSet[position].stopTime
        holder.view.interval = dataSet[position].interval
        holder.view.night = dataSet[position].night
        holder.view.transportId = dataSet[position].transportId
    }


    override fun getItemCount(): Int { return dataSet.size }


    inner class TimesViewHolder(val view: TimeView): RecyclerView.ViewHolder(view)
}