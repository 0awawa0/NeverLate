package com.awawa.neverlate.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


class Entities {

    @Entity(tableName = "routes")
    class Routes(
        @PrimaryKey(autoGenerate = true) @NotNull var _id : Int = 0,
        @ColumnInfo(name = "RouteName") var routeName : String = "0",
        @ColumnInfo(name = "RouteID") @NotNull var routeId : Int = 0,
        @ColumnInfo(name = "TransportID") @NotNull var transportId: Int = 0,
        @ColumnInfo(name = "RouteNumber") @NotNull var routeNumber : String = "0",
        @ColumnInfo(name = "Reverse") @NotNull var reverse : Int = 0
    )

    @Entity(tableName = "stops")
    class Stops(
        @PrimaryKey(autoGenerate = true) val _id : Int = 0,
        @ColumnInfo(name = "StopName") var stopName : String = "0",
        @ColumnInfo(name = "RouteID") var routeId: Int = 0,
        @ColumnInfo(name = "StopID") var stopId: Int = 0,
        @ColumnInfo(name = "StopNumber") var stopNumber : Int = 0,
        @ColumnInfo(name = "TransportID") var transportId : Int = 0
    )

    @Entity(tableName = "times")
    data class Times(
        @ColumnInfo(name = "StopTime") var stopTime: Int = 0,
        @ColumnInfo(name = "StopID") var stopId: Int = 0,
        @ColumnInfo(name = "Night") var night: Int = 0,
        @ColumnInfo(name = "Weekend") var weekend: Int = 0,
        @ColumnInfo(name = "TransportID") var transportId: Int = 0
    ) {
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name="_id")
        var _id: Int = 0
    }

    @Entity(tableName = "notifications")
    data class Notifications(
        @ColumnInfo(name = "TimeId") var timeId: Int = 0,
        @ColumnInfo(name = "RouteNumber") var routeNumber: String = "",
        @ColumnInfo(name = "StopName") var stopName: String = "",
        @ColumnInfo(name = "Time") var time: Long = 0,
        @ColumnInfo(name = "Delta") var delta: Int = 0,
        @ColumnInfo(name = "Repeat") var repeat: Boolean = false
    ) {
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name="_id")
        var _id: Int = 0
    }
}