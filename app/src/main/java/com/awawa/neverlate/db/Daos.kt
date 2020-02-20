package com.awawa.neverlate.db


import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update


class Daos {

    @Dao
    interface RouteDao {
        @Query("SELECT * FROM routes WHERE TransportID == :id AND Reverse == 0 ORDER BY RouteNumber")
        suspend fun getRoutes(id: Int): List<Entities.Routes>
    }

    @Dao
    interface StopDao {
        @Query("SELECT * FROM stops WHERE RouteID == :routeId ORDER BY StopNumber")
        suspend fun getAllStops(routeId: Int) : List<Entities.Stops>

        @Query("SELECT TransportID FROM stops WHERE StopID == :stopId")
        suspend fun resolveTransportID(stopId: Int): Int?
    }


    @Dao
    interface TimesDao {
        @Query("SELECT * FROM times WHERE StopID == :stopId AND Weekend == :weekend ORDER BY _id")
        suspend fun getStops(stopId: Int, weekend: Boolean): List<Entities.Times>

        @Update
        suspend fun updateTable(stops: List<Entities.Times>)

        @Query("SELECT * FROM times")
        suspend fun getAllTimes(): List<Entities.Times>
    }
}