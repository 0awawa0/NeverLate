package com.awawa.neverlate.ui.settings

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import com.awawa.neverlate.RVItemClickListener
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.custom_views.NotificationView
import kotlinx.android.synthetic.main.layout_notification_view.view.*


class NotificationsAdapter(private val clickListener: RVItemClickListener)
    : RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>() {

    private val dataSet = ArrayList<Entities.Notifications>()

    fun updateDataSet(notifications: List<Entities.Notifications>) {
        dataSet.clear()
        dataSet.addAll(notifications)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val view = NotificationView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return NotificationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        holder.view.id = dataSet[position].timeId
        holder.view.time = dataSet[position].time.toString()
        holder.view.delta = dataSet[position].delta
        holder.view.routeNumber = dataSet[position].routeNumber
        holder.view.stopName = dataSet[position].stopName
        holder.view.repeat = dataSet[position].repeat
    }

    override fun getItemCount(): Int { return dataSet.size }

    inner class NotificationsViewHolder(val view: NotificationView): RecyclerView.ViewHolder(view) {
        init {
            view.btCancel.setOnClickListener { view -> run {
                clickListener.onClick(view)
            }}
        }
    }
}