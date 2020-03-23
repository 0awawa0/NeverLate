package com.awawa.neverlate.ui.settings


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awawa.neverlate.RVItemClickListener
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.ui.custom_views.NotificationView
import com.awawa.neverlate.utils.timeStampToTime


class NotificationsAdapter(private val clickListener: RVItemClickListener)
    : RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>() {

    private val dataSet = ArrayList<Entities.Notifications>()

    fun updateDataSet(notifications: List<Entities.Notifications>) {
        dataSet.clear()
        dataSet.addAll(notifications)
        notifyDataSetChanged()
    }

    fun deleteNotification(id: Int) {
        for (i in dataSet.indices)
            if (dataSet[i].timeId == id) {
                dataSet.removeAt(i)
                notifyItemRemoved(i)
                break
            }
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
        holder.view.transportId = dataSet[position].transportId
        holder.view.time = timeStampToTime(dataSet[position].time)
        holder.view.delta = dataSet[position].delta
        holder.view.routeNumber = dataSet[position].routeNumber
        holder.view.stopName = dataSet[position].stopName
        holder.view.repeat = dataSet[position].repeat
        holder.view.btCancel.id = dataSet[position].timeId
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