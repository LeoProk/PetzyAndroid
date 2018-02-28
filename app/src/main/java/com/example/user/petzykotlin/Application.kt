package com.example.user.petzykotlin

/**
 * Application class have volley rellated methods.
 * current fragment tag
 * current location
 */

import android.app.Application
import android.location.Location

class AppController : Application() {
    //current location of user
    lateinit var currentLocation:Location
    //tag used to id frags
    lateinit var fragmentTag:String

    override fun onCreate() {
        super.onCreate()
        getCurrentLocation()
    }

    private fun getCurrentLocation(){

    }
}
