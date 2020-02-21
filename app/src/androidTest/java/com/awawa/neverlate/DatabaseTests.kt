package com.awawa.neverlate


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.awawa.neverlate.db.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DatabaseTests {

    private lateinit var database: Database
    private lateinit var routeDao: Daos.RouteDao
    private lateinit var stopDao: Daos.StopDao
    private lateinit var timesDao: Daos.TimesDao


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.databaseBuilder(context, Database::class.java, databaseName)
            .allowMainThreadQueries()
            .createFromAsset(assetDatabaseFile)
            .build()
        routeDao = database.routesDao()
        stopDao = database.stopsDao()
        timesDao = database.timesDao()
    }


    @Test
    fun checkAllRoutesHasStops() {

        val routes = routeDao.getAllRoutes()
        println("Total count of routes to be checked: ${routes.size}")
        for (route in routes) {
            val stops = stopDao.getAllStops(route.routeId)
            assert(stops.isNotEmpty()) {
                "No stops for routeId: ${route.routeId}"
            }
            println("Route ${route.routeId} checked")
        }
    }


    @Test
    fun checkAllRoutesHasAtLeastOneTimeTable() {
        val stops = stopDao.getAllStops()
        print("Total count of stops to be checked: ${stops.size}")
        for (stop in stops) {
            val weekDayTimeTable = timesDao.getTimeTable(stop.stopId, false)
            val weekEndTimeTable = timesDao.getTimeTable(stop.stopId, true)
            assert(weekDayTimeTable.isNotEmpty() || weekEndTimeTable.isNotEmpty())
            println("Checked stopId ${stop.stopId}. Has weekDay: ${weekDayTimeTable.isNotEmpty()}. Has weekEnd: ${weekEndTimeTable.isNotEmpty()}")
        }
    }


    @After
    fun closeDb() { database.close() }
}