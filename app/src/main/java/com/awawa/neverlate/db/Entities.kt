package com.awawa.neverlate.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


class Entities {

    @Entity(tableName = "routes")
    class Routes(
        @PrimaryKey @NotNull var _id : Int = 0,
        @ColumnInfo(name = "RouteName") var routeName : String = "0",
        @ColumnInfo(name = "RouteID") @NotNull var routeId : Int = 0,
        @ColumnInfo(name = "TransportID") @NotNull var transportId: Int = 0,
        @ColumnInfo(name = "RouteNumber") @NotNull var routeNumber : String = "0",
        @ColumnInfo(name = "Reverse") @NotNull var reverse : Int = 0
    )

    @Entity(tableName = "stops")
    class Stops(
        @PrimaryKey val _id : Int = 0,
        @ColumnInfo(name = "StopName") var stopName : String = "0",
        @ColumnInfo(name = "RouteID") var routeId: Int = 0,
        @ColumnInfo(name = "StopID") var stopId: Int = 0,
        @ColumnInfo(name = "StopNumber") var stopNumber : Int = 0,
        @ColumnInfo(name = "TransportID") var transportId : Int = 0
    )

    @Entity(tableName = "times")
    class Times(
        @PrimaryKey @ColumnInfo(name="_id") @NotNull var _id : Int = 0,
        @ColumnInfo(name = "StopTime") @NotNull var stopTime : String = "0",
        @ColumnInfo(name = "StopID") @NotNull var stopId : Int = 0,
        @ColumnInfo(name = "Interval") @NotNull var interval : Int = 0,
        @ColumnInfo(name = "Night") @NotNull var night : Int = 0,
        @ColumnInfo(name = "Weekend") @NotNull var weekend : Int = 0,
        @ColumnInfo(name = "TransportID") @NotNull var transportId: Int = 0
    )
}