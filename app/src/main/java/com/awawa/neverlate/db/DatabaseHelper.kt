package com.awawa.neverlate.db


import android.content.Context
import androidx.room.Room


const val assetDatabaseFile = "database/database.db"
const val databaseName = "NeverlateDB"

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