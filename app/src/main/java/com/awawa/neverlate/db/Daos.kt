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

        @Query("DELETE FROM stops WHERE stopId == :stopId")
        fun deleteStop(stopId: Int)

        @Query("SELECT * FROM stops WHERE stopId == :stopId")
        fun checkStop(stopId: Int): Entities.Stops?

        @Update
        fun updateStop(stop: Entities.Stops)

        @Insert
        fun addStop(stop: Entities.Stops)
    }


    @Dao
    interface TimesDao {

        @Insert
        fun putTime(time: Entities.Times)

        @Query("SELECT * FROM times WHERE StopID == :stopId AND Weekend == :weekend ORDER BY StopTime")
        fun getTimeTable(stopId: Int, weekend: Boolean): List<Entities.Times>

        @Query("DELETE FROM times WHERE StopID == :stopId")
        fun deleteTimetableForStop(stopId: Int)

        @Query("SELECT * FROM times WHERE stopTime == :time AND stopId == :stopId")
        fun checkTime(stopId: Int, time: Int): Entities.Times?

        @Query("DELETE FROM times WHERE _id == :id")
        fun deleteTime(id: Int)

        @Update
        fun updateTime(time: Entities.Times)
    }
}