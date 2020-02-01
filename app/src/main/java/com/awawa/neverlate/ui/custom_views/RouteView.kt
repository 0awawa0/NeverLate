package com.awawa.neverlate.ui.custom_views


import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.awawa.neverlate.R


class RouteView(
    context: Context,
    transportId: Int,
    routeNumber: String,
    routeName: String
) : LinearLayout(context) {

    private val view: LinearLayout = LayoutInflater.from(context)
        .inflate(R.layout.route_view, this, false) as LinearLayout
    private val tvRouteNumber = view.findViewById<TextView>(R.id.tvRouteNumber)
    private val tvRouteName = view.findViewById<TextView>(R.id.tvRouteName)

    init {
        tvRouteNumber.text = routeNumber
        tvRouteName.text = routeName
        when (transportId) {
            1 -> {
                tvRouteNumber.setTextColor(context.resources.getColor(R.color.colorTram))
                view.background = context.resources.getDrawable(R.drawable.tram_route_view_background)
            }

            2 -> {
                tvRouteNumber.setTextColor(context.resources.getColor(R.color.colorTrolley))
                view.background = context.resources.getDrawable(R.drawable.trolley_route_view_background)
            }

            3 -> {
                tvRouteNumber.setTextColor(context.resources.getColor(R.color.colorBus))
                view.background = context.resources.getDrawable(R.drawable.bus_route_view_background)
            }

            4 -> {
                tvRouteNumber.setTextColor(context.resources.getColor(R.color.colorMarsh))
                view.background = context.resources.getDrawable(R.drawable.marsh_route_view_background)
            }
        }

        this.addView(view)
    }
}