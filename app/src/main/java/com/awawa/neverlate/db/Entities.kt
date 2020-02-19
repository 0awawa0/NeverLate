package com.awawa.neverlate.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

class Entities{

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
        @ColumnInfo(name = "StopName") val stopName : String = "0",
        @ColumnInfo(name = "RouteID") val routeId: Int = 0,
        @ColumnInfo(name = "StopID") val stopId: Int = 0,
        @ColumnInfo(name = "StopNumber") val stopNumber : Int = 0,
        @ColumnInfo(name = "TransportID") val transportId : Int = 0
    )

    @Entity(tableName = "times")
    class Times(
        @PrimaryKey @ColumnInfo(name="_id") val _id : Int = 0,
        @ColumnInfo(name = "StopTime") val stopTime : String = "0",
        @ColumnInfo(name = "StopID") val stopId : Int = 0,
        @ColumnInfo(name = "Interval") val interval : Int = 0,
        @ColumnInfo(name = "Night") val night : Int = 0,
        @ColumnInfo(name = "Weekend") val weekend : Int = 0
    )
}