package com.awawa.neverlate.db

import androidx.room.Dao
import androidx.room.Query

class Daos {

    @Dao
    interface RouteDao {

        @Query("SELECT * FROM routes")
        fun getAll() : List<Entities.Routes>

        @Query("SELECT * FROM routes WHERE TransportID == 1 AND Reverse == 0 ORDER BY RouteNumber")
        fun getAllTramRoutes() : List<Entities.Routes>

        @Query("SELECT * FROM routes WHERE TransportID == 2 AND Reverse == 0 ORDER BY RouteNumber")
        fun getAllTrolleyRoutes() : List<Entities.Routes>

        @Query("SELECT * FROM routes WHERE TransportID == 3 AND Reverse == 0 ORDER BY RouteNumber")
        fun getAllBusRoutes() : List<Entities.Routes>

        @Query("SELECT * FROM routes WHERE TransportID == 4 AND Reverse == 0 ORDER BY RouteNumber")
        fun getAllMarshRoutes() : List<Entities.Routes>

        @Query("SELECT * FROM routes WHERE TransportID == :transportId AND Reverse == 0 ORDER BY RouteNumber")
        suspend fun getAllRoutes(transportId : Int) : List<Entities.Routes>
    }

    @Dao
    interface StopDao {
        @Query("SELECT * FROM stops WHERE RouteID == :routeId ORDER BY StopNumber")
        suspend fun getAllStops(routeId: Int) : List<Entities.Stops>
    }
}