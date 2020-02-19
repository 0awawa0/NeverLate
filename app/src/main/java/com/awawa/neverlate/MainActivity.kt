package com.awawa.neverlate


import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(R.layout.activity_main),
    NavController.OnDestinationChangedListener {

    private val TAG = "MainActivity"
    private lateinit var appBarConfiguration: AppBarConfiguration
    var transportId = 1
    var routeId = 1
    lateinit var navController: NavController

    private val destinationToColorMap = mapOf(
        R.id.nav_tram to R.color.item_tram,
        R.id.nav_trolley to R.color.item_trolley,
        R.id.nav_bus to R.color.item_bus,
        R.id.nav_marsh to R.color.item_marsh
    )
    private val destinationToTransportIdMap = mapOf(
        R.id.nav_tram to 1,
        R.id.nav_trolley to 2,
        R.id.nav_bus to 3,
        R.id.nav_marsh to 4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val animationDrawable = toolbar.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(2500)
        animationDrawable.start()
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_tram, R.id.nav_trolley, R.id.nav_bus, R.id.nav_marsh)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottom_nav_view.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        mainLoadingPanel.visibility = View.VISIBLE

        if (destination.id in destinationToColorMap.keys) {
            transportId = destinationToTransportIdMap[destination.id]!!
            if (isMarshmallowOrHigher()) {
                bottom_nav_view.itemIconTintList = resources.getColorStateList(
                    destinationToColorMap[destination.id]!!, theme
                )
                bottom_nav_view.itemTextColor = bottom_nav_view.itemIconTintList
            } else {
                bottom_nav_view.itemIconTintList = resources.getColorStateList(
                    destinationToColorMap[destination.id]!!, theme
                )
                bottom_nav_view.itemTextColor = bottom_nav_view.itemIconTintList
            }
        } else { return }
    }
}
