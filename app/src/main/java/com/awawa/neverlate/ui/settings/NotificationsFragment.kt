package com.awawa.neverlate.ui.settings


import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.awawa.neverlate.MainActivity
import com.awawa.neverlate.PreCachedLayoutManager
import com.awawa.neverlate.R
import com.awawa.neverlate.RVItemClickListener
import com.awawa.neverlate.db.Entities
import com.awawa.neverlate.utils.toast
import kotlinx.android.synthetic.main.fragment_notifications_settings.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NotificationsFragment : Fragment(), RVItemClickListener {

    private val adapter = NotificationsAdapter(this)
    private val presenter = NotificationsPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications_settings, container, false)

        val size = Point()
        requireActivity().windowManager.defaultDisplay.getSize(size)
        root.rvNotifications.layoutManager = PreCachedLayoutManager(requireContext(), size.y)
        root.rvNotifications.adapter = adapter
        presenter.loadNotifications()

        return root
    }

    suspend fun notificationDeleted(timeId: Int) {
        withContext(Dispatchers.Main) {
            adapter.deleteNotification(timeId)
        }
    }

    suspend fun updateNotificationsList(notifications: List<Entities.Notifications>) {
        withContext(Dispatchers.Main) {
            adapter.updateDataSet(notifications)
            (requireActivity() as MainActivity).hideLoadingPanel()
        }
    }

    override fun onClick(view: View) {
        presenter.deleteNotification(view.id)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?,
        position: Int
    ) {
    }
}