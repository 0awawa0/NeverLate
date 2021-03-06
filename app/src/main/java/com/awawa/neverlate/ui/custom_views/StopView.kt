package com.awawa.neverlate.ui.custom_views


import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.awawa.neverlate.R


class StopView(
    context: Context,
    transportId: Int,
    stopName: String
) : LinearLayout(context) {

    var stopName = stopName
        set(value) {
            field = value
            tvStopName.text = value
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
        .inflate(R.layout.layout_stop_view, this, false) as LinearLayout
    private val tvStopName = view.findViewById<TextView>(R.id.tvStopName)

    init { this.addView(view) }
}