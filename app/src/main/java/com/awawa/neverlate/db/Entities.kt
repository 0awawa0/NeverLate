package com.awawa.neverlate.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

class Entities{

    @Entity(tableName = "routes")
    class Routes(
        @PrimaryKey @NotNull var _id : Int?,
        @ColumnInfo(name = "RouteName") var routeName : String?,
        @ColumnInfo(name = "RouteID") @NotNull var routeId : Int?,
        @ColumnInfo(name = "TransportID") @NotNull var transportId: Int?,
        @ColumnInfo(name = "RouteNumber") @NotNull var routeNumber : String?,
        @ColumnInfo(name = "Reverse") @NotNull var reverse : Int?
    )

    @Entity(tableName = "stops")
    class Stops(
        @PrimaryKey val _id : Int?,
        @ColumnInfo(name = "StopName") val stopName : String?,
        @ColumnInfo(name = "RouteID") val routeId: Int?,
        @ColumnInfo(name = "StopID") val stopId: Int?,
        @ColumnInfo(name = "StopNumber") val stopNumber : Int?,
        @ColumnInfo(name = "TransportID") val transportId : Int?
    )

    @Entity(tableName = "times")
    class Times(
        @PrimaryKey val _id : Int?,
        @ColumnInfo(name = "StopTime") val stopTime : String?,
        @ColumnInfo(name = "StopID") val stopId : Int?,
        @ColumnInfo(name = "Interval") val interval : Int?,
        @ColumnInfo(name = "Night") val night : Int?,
        @ColumnInfo(name = "Weekend") val weekend : Int?
    )
}