package com.awawa.neverlate.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val migration1_2 = object: Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE 'notifications' (" +
                "'_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "'TimeId' INTEGER NOT NULL UNIQUE," +
                "'RouteNumber' TEXT NOT NULL," +
                "'StopName' TEXT NOT NULL," +
                "'Time' INTEGER NOT NULL," +
                "'Delta' INTEGER NOT NULL," +
                "'Repeat' INTEGER NOT NULL" +
                ");")
    }
}

val migration2_3 = object: Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE notifications ADD COLUMN TransportID INTEGER NOT NULL DEFAULT 0")
    }
}

@Database(
    entities = [
        Entities.Routes::class,
        Entities.Stops::class,
        Entities.Times::class,
        Entities.Notifications::class
    ],
    exportSchema = false,
    version = 3
)
abstract class Database : RoomDatabase() {
    abstract fun routesDao() : Daos.RouteDao
    abstract fun stopsDao() : Daos.StopDao
    abstract fun timesDao() : Daos.TimesDao
    abstract fun notificationsDao() : Daos.NotificationsDao
}