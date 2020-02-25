package com.awawa.neverlate


import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.awawa.neverlate.db.TRANSPORT_ID_BUS
import com.awawa.neverlate.db.TRANSPORT_ID_MARSH
import com.awawa.neverlate.db.TRANSPORT_ID_TRAM
import com.awawa.neverlate.db.TRANSPORT_ID_TROLLEY
import com.awawa.neverlate.ui.routes.RoutesFragment
import com.awawa.neverlate.utils.isMarshmallowOrHigher
import com.awawa.neverlate.utils.showAddRouteDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(R.layout.activity_main),
    NavController.OnDestinationChangedListener {

    private val tag = "MainActivity"
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    private val destinationToColorMap = mapOf(
        R.id.nav_tram to R.color.item_tram,
        R.id.nav_trolley to R.color.item_trolley,
        R.id.nav_bus to R.color.item_bus,
        R.id.nav_marsh to R.color.item_marsh
    )

    private val destinationToTransportIdMap = mapOf(
        R.id.nav_tram to TRANSPORT_ID_TRAM,
        R.id.nav_trolley to TRANSPORT_ID_TROLLEY,
        R.id.nav_bus to TRANSPORT_ID_BUS,
        R.id.nav_marsh to TRANSPORT_ID_MARSH
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

        if (destination.id in destinationToColorMap.keys) {
            if (isMarshmallowOrHigher()) {
                bottom_nav_view.itemIconTintList = resources.getColorStateList(
                    destinationToColorMap.getValue(destination.id), theme
                )
                bottom_nav_view.itemTextColor = bottom_nav_view.itemIconTintList
            } else {
                bottom_nav_view.itemIconTintList = resources.getColorStateList(
                    destinationToColorMap.getValue(destination.id), theme
                )
                bottom_nav_view.itemTextColor = bottom_nav_view.itemIconTintList
            }
        } else { return }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.plus) {
            when (navController.currentDestination?.id){
                R.id.nav_tram,
                R.id.nav_trolley,
                R.id.nav_bus,
                R.id.nav_marsh -> {
                    (supportFragmentManager.fragments[0] as RoutesFragment).addNewRoute()
                }

                R.id.nav_stops -> {}
                R.id.nav_times -> {}
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
