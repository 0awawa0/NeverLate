package com.awawa.neverlate.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


class Daos {

    @Dao
    interface RouteDao {
        @Query("SELECT * FROM routes WHERE TransportID == :id AND Reverse == 0 ORDER BY RouteNumber")
        fun getRoutes(id: Int): List<Entities.Routes>

        @Query("SELECT * FROM routes")
        fun getAllRoutes(): List<Entities.Routes>
    }

    @Dao
    interface StopDao {
        @Query("SELECT * FROM stops WHERE RouteID == :routeId ORDER BY StopNumber")
        fun getAllStops(routeId: Int) : List<Entities.Stops>

        @Query("SELECT * FROM stops")
        fun getAllStops(): List<Entities.Stops>

        @Query("SELECT TransportID FROM stops WHERE StopID == :stopId")
        fun resolveTransportID(stopId: Int): Int?
    }


    @Dao
    interface TimesDao {

        @Insert
        fun putTime(time: Entities.NewTimes)

        @Query("SELECT * FROM times WHERE StopID == :stopId AND Weekend == :weekend ORDER BY StopTime")
        fun getTimeTable(stopId: Int, weekend: Boolean): List<Entities.NewTimes>
    }
}