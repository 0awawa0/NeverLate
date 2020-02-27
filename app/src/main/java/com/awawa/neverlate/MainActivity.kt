package com.awawa.neverlate


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
import com.awawa.neverlate.utils.isMarshmallowOrHigher
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


val transportIdToColorMap = mapOf(
    TRANSPORT_ID_TRAM to R.color.colorTram,
    TRANSPORT_ID_BUS to R.color.colorBus,
    TRANSPORT_ID_TROLLEY to R.color.colorTrolley,
    TRANSPORT_ID_MARSH to R.color.colorMarsh
)

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

    private val destinationToBackground = mapOf(
        R.id.nav_tram to R.drawable.background_toolbar_tram,
        R.id.nav_trolley to R.drawable.background_toolbar_trolley,
        R.id.nav_bus to R.drawable.background_toolbar_bus,
        R.id.nav_marsh to R.drawable.background_toolbar_marsh
    )

    private var menuItemSelectCallback: MenuItemSelectCallback? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
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
            toolbar.background = getDrawable(destinationToBackground.getValue(destination.id))
        } else { return }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.plus) {
            if (menuItemSelectCallback != null)
                return menuItemSelectCallback!!.onItemSelected(item)
            return false
        }
        return super.onOptionsItemSelected(item)
    }


    fun registerMenuItemSelectCallback(callback: MenuItemSelectCallback?) {
        this.menuItemSelectCallback = callback
    }


    interface MenuItemSelectCallback {
        fun onItemSelected(item: MenuItem): Boolean
    }
}
