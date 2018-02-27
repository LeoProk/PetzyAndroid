package com.example.user.petzykotlin

/**
 * Application class have volley rellated methods.
 * current fragment tag
 * current location
 */

import android.app.Application
import android.location.Location

class AppController : Application() {
    lateinit var cuurentLocation:Location
}
