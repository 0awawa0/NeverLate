package com.awawa.neverlate.ui.custom_views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorStateListDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import androidx.core.content.ContextCompat
import com.awawa.neverlate.R
import com.awawa.neverlate.db.TRANSPORT_ID_BUS
import com.awawa.neverlate.db.TRANSPORT_ID_MARSH
import com.awawa.neverlate.db.TRANSPORT_ID_TRAM
import com.awawa.neverlate.db.TRANSPORT_ID_TROLLEY


class NotificationView : HorizontalScrollView {

    private val view = LayoutInflater.from(context).inflate(
        R.layout.layout_notification_view,
        this,
        false
    ) as GridLayout

    private val tvTime: TextView = view.findViewById(R.id.tvTime)
    private val tvDelta: TextView = view.findViewById(R.id.tvDelta)
    private val tvRouteNumber: TextView = view.findViewById(R.id.tvRouteNumber)
    private val tvStopName: TextView = view.findViewById(R.id.tvStopName)
    private val tvRepeat: TextView = view.findViewById(R.id.tvRepeat)
    private val imgTransport: ImageView = view.findViewById(R.id.imgTransport)
    val btCancel: Button = view.findViewById(R.id.btCancel)

    var transportId: Int
        set(value) {
            field = value

            when(field) {
                TRANSPORT_ID_TRAM -> {
                    imgTransport.setImageDrawable(context.getDrawable(R.drawable.menu_item_tram))
                    imgTransport.imageTintList = ContextCompat.getColorStateList(context, R.color.colorTram)
                }
                TRANSPORT_ID_TROLLEY -> {
                    imgTransport.setImageDrawable(context.getDrawable(R.drawable.trolley_menu_item))
                    imgTransport.imageTintList = ContextCompat.getColorStateList(context, R.color.colorTrolley)
                }
                TRANSPORT_ID_BUS -> {
                    imgTransport.setImageDrawable(context.getDrawable(R.drawable.menu_item_bus))
                    imgTransport.imageTintList = ContextCompat.getColorStateList(context, R.color.colorBus)
                }
                TRANSPORT_ID_MARSH -> {
                    imgTransport.setImageDrawable(context.getDrawable(R.drawable.menu_item_marsh))
                    imgTransport.imageTintList = ContextCompat.getColorStateList(context, R.color.colorMarsh)
                }
            }
        }
    var time: String = tvTime.text.toString()
        set(value) {
            field = value
            tvTime.text = field
        }

    var delta: Int = tvDelta.text.toString().toInt()
        set(value) {
            field = value
            tvDelta.text = field.toString()
        }

    var routeNumber: String = tvRouteNumber.text.toString()
        set(value) {
            field = value
            tvRouteNumber.text = field
        }

    var stopName: String = tvStopName.text.toString()
        set(value) {
            field = value
            tvStopName.text = field
        }

    var repeat: Boolean = tvRepeat.text.toString() == "Да"
        set(value) {
            field = value
            tvRepeat.text = if (field) "Да" else "Нет"
        }


    constructor(context: Context): super(context) {
        addView(view)
        transportId = id
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        if (attrs != null) {
            val theme = context.theme

            val a = theme.obtainStyledAttributes(attrs, R.styleable.NotificationView, 0, 0)
            for (i in 0 until a.indexCount) {
                when (a.getIndex(i)) {
                    R.styleable.NotificationView_time -> tvTime.text = a.getString(i)
                    R.styleable.NotificationView_delta -> tvDelta.text = a.getInt(i, 0).toString()
                    R.styleable.NotificationView_routeNumber -> tvRouteNumber.text = a.getString(i)
                    R.styleable.NotificationView_stopName -> tvStopName.text = a.getString(i)
                    R.styleable.NotificationView_repeat -> tvRepeat.text = if (a.getBoolean(i, false)) "Да" else "Нет"
                }
            }
            a.recycle()
        }
        addView(view)
        transportId = id
    }
}