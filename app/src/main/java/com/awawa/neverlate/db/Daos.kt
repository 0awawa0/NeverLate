package com.awawa.neverlate.db


import androidx.room.*


class Daos {

    @Dao
    interface RouteDao {
        @Query("SELECT * FROM routes WHERE TransportID == :id AND Reverse == 0 ORDER BY RouteNumber")
        fun getRoutes(id: Int): List<Entities.Routes>

        @Query("SELECT * FROM routes")
        fun getAllRoutes(): List<Entities.Routes>

        @Insert
        fun addNewRoute(route: Entities.Routes)

        @Query("SELECT * FROM routes WHERE routeId == :routeId")
        fun checkRoute(routeId: Int): Entities.Routes?

        @Query("DELETE FROM routes WHERE routeId == :routeId")
        fun deleteRoute(routeId: Int)
    }

    @Dao
    interface StopDao {
        @Query("SELECT * FROM stops WHERE RouteID == :routeId ORDER BY StopNumber")
        fun getAllStops(routeId: Int) : List<Entities.Stops>

        @Query("SELECT * FROM stops")
        fun getAllStops(): List<Entities.Stops>

        @Query("SELECT TransportID FROM stops WHERE StopID == :stopId")
        fun resolveTransportID(stopId: Int): Int?

        @Query("DELETE FROM stops WHERE routeId == :routeId")
        fun deleteStopsForRoute(routeId: Int)
    }


    @Dao
    interface TimesDao {

        @Insert
        fun putTime(time: Entities.NewTimes)

        @Query("SELECT * FROM times WHERE StopID == :stopId AND Weekend == :weekend ORDER BY StopTime")
        fun getTimeTable(stopId: Int, weekend: Boolean): List<Entities.NewTimes>

        @Query("DELETE FROM times WHERE StopID == :stopId")
        fun deleteTimetableForStop(stopId: Int)
    }
}