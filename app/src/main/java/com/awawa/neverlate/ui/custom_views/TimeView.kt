package com.awawa.neverlate.ui.custom_views


import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.awawa.neverlate.R


class TimeView(context: Context,
               time: String,
               night: Int,
               transportId: Int): LinearLayout(context) {

    var time = time
        set(value) {
            field = value
            tvTime.text = value
        }

    var night = night
        set(value) {
            field = value
            if (value == 1) {
                view.background =
                    context.resources.getDrawable(R.drawable.background_night_route_view)
            }
        }

    var transportId = transportId
        set(value) {
            field = value
            when (value) {
                1 -> {
                    view.background =
                        context.resources.getDrawable(R.drawable.background_tram_route_view)
                }

                2 -> {
                    view.background =
                        context.resources.getDrawable(R.drawable.background_trolley_route_view)
                }

                3 -> {
                    view.background =
                        context.resources.getDrawable(R.drawable.background_bus_route_view)
                }

                4 -> {
                    view.background =
                        context.resources.getDrawable(R.drawable.background_marsh_route_view)
                }
            }
        }

    private val view: LinearLayout = LayoutInflater.from(context)
        .inflate(R.layout.layout_time_view, this, false) as LinearLayout
    private val tvTime = view.findViewById<TextView>(R.id.tvTime)

    init { this.addView(view) }
}