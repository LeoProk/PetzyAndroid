package me.leoprok.petzyandroid.pojos

import android.location.Location
import kotlin.math.round

/**
 * Created by user on 2/26/18.
 */


class Park(var address: String, var title: String, var user: String, var locationPark: LocationPark,
           var image: String, var camera: String): Comparable<Park> {


    //rounding the distance to park from current locationPark and return it
    private fun  getDistance() :Int {
        val parkLocation = Location("Park Location")
    parkLocation.latitude = locationPark.lat.toDouble()
    parkLocation.longitude = locationPark.lng.toDouble()
        return 0//round()
    }

    //check the distance between the current locationPark and the parks locationPark
    // using getDistance() and return the final distance
    override fun compareTo(other: Park): Int {
        val comparedPark  = other.getDistance()
        val currentDistance  = getDistance()
        return currentDistance - comparedPark
    }
}