package com.awawa.neverlate.db

import android.content.Context
import android.util.Log
import androidx.room.Room

class DatabaseHelper() {

    private val TAG = "DatabaseHelper"
    private val databaseName = "NeverLateDB"
    private val assetDatabaseFile = "database/database.db"

    companion object {
        private lateinit var instance : DatabaseHelper

        private fun getInstance(context: Context) : DatabaseHelper {
            if (!this::instance.isInitialized){
                instance = DatabaseHelper(context)
            }
            return instance
        }

        public fun getDatabase(context: Context) : Database {
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