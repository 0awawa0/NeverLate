package com.awawa.neverlate.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Entities.Routes::class, Entities.Stops::class, Entities.Times::class],
    exportSchema = false,
    version = 1
)
abstract class Database : RoomDatabase(){
    abstract fun routesDao() : Daos.RouteDao
    abstract fun stopsDao() : Daos.StopDao
    abstract fun timesDao() : Daos.TimesDao
}