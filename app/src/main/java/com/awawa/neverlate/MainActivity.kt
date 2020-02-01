package com.awawa.neverlate


import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
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


class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private val TAG = "MainActivity"
    private lateinit var appBarConfiguration: AppBarConfiguration
    var transportId = 1
    var routeId = 1
    lateinit var loadingPannel : View
    private lateinit var navView : BottomNavigationView
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val animationDrawable = toolbar.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(2500)
        animationDrawable.start()
        setSupportActionBar(toolbar)

        loadingPannel = findViewById(R.id.mainLoadingPanel)

        navView = findViewById(R.id.bottom_nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_tram, R.id.nav_trolley, R.id.nav_bus, R.id.nav_marsh)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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
        loadingPannel.visibility = View.VISIBLE
        when (destination.id) {
            R.id.nav_tram -> {
                transportId = 1
                navView.itemIconTintList = resources.getColorStateList(R.color.item_tram)
                navView.itemTextColor = resources.getColorStateList(R.color.item_tram)
            }
            R.id.nav_trolley -> {
                transportId = 2
                navView.itemIconTintList = resources.getColorStateList(R.color.item_trolley)
                navView.itemTextColor = resources.getColorStateList(R.color.item_trolley)
            }
            R.id.nav_bus -> {
                transportId = 3
                navView.itemIconTintList = resources.getColorStateList(R.color.item_bus)
                navView.itemTextColor = resources.getColorStateList(R.color.item_bus)
            }
            R.id.nav_marsh -> {
                transportId = 4
                navView.itemIconTintList = resources.getColorStateList(R.color.item_marsh)
                navView.itemTextColor = resources.getColorStateList(R.color.item_marsh)
            }
        }
    }
}
