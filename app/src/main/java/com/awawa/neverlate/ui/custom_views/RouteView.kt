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

    var routeName: String = routeName
        set(value) {
            field = value
            tvRouteName.text = value
        }

    var routeNumber: String = routeNumber
        set(value) {
            field = value
            tvRouteNumber.text = value
        }

    var transportId: Int = transportId
        set(value) {
            field = value
            when (value) {
                1 -> {
                    tvRouteNumber.setTextColor(context.resources.getColor(R.color.colorTram))
                    view.background = context.resources.getDrawable(R.drawable.background_tram_route_view)
                }

                2 -> {
                    tvRouteNumber.setTextColor(context.resources.getColor(R.color.colorTrolley))
                    view.background = context.resources.getDrawable(R.drawable.background_trolley_route_view)
                }

                3 -> {
                    tvRouteNumber.setTextColor(context.resources.getColor(R.color.colorBus))
                    view.background = context.resources.getDrawable(R.drawable.background_bus_route_view)
                }

                4 -> {
                    tvRouteNumber.setTextColor(context.resources.getColor(R.color.colorMarsh))
                    view.background = context.resources.getDrawable(R.drawable.background_marsh_route_view)
                }
            }
        }

    private val view: LinearLayout = LayoutInflater.from(context)
        .inflate(R.layout.layout_route_view, this, false) as LinearLayout
    private val tvRouteNumber = view.findViewById<TextView>(R.id.tvRouteNumber)
    private val tvRouteName = view.findViewById<TextView>(R.id.tvRouteName)

    init { this.addView(view) }
}