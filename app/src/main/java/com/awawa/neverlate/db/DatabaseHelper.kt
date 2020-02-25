package com.awawa.neverlate.db


import android.content.Context
import androidx.room.Room


const val assetDatabaseFile = "database/database.db"
const val databaseName = "NeverlateDB"

const val TRANSPORT_ID_TRAM = 1
const val TRANSPORT_ID_TROLLEY = 2
const val TRANSPORT_ID_BUS = 3
const val TRANSPORT_ID_MARSH = 4


class DatabaseHelper() {

    private val tag = "DatabaseHelper"

    companion object {
        private lateinit var instance : DatabaseHelper

        private fun getInstance(context: Context) : DatabaseHelper {
            if (!this::instance.isInitialized){
                instance = DatabaseHelper(context)
            }
            return instance
        }

        fun getDatabase(context: Context) : Database {
            return getInstance(context).database
        }

        fun checkRouteId(routeId: Int): Boolean {
            return instance.database.routesDao().checkRoute(routeId) != null
        }
    }

    private lateinit var database: Database

    private constructor(context: Context) : this() {
        if (!::database.isInitialized){
            database = Room.databaseBuilder(context, Database::class.java, databaseName)
                .allowMainThreadQueries()
                .createFromAsset(assetDatabaseFile)
                .build()
        }
    }
}