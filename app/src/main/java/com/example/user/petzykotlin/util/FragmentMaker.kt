package com.example.user.petzykotlin.util

import android.app.Fragment
import android.content.Context
import android.app.Activity
import me.leoprok.petzyandroid.Application
import me.leoprok.petzyandroid.R


/**
 * adds new fragment
 */

class FragmentMaker(var context : Context, var fragment : Fragment, var tag:String, var addToBackStack:Boolean ){

    init {
        val appController = context.getApplicationContext() as Application
        appController.fragmentTag = tag
        if (addToBackStack) {
            val fragment = fragment
            val fragmentManager = (context as Activity)
                    .fragmentManager
            fragmentManager.beginTransaction().add(R.id.nav_view, fragment, tag)
                    .addToBackStack(tag).commit()

        } else {
            val fragment = fragment
            val fragmentManager = (context as Activity)
                    .fragmentManager
            fragmentManager.beginTransaction().add(R.id.nav_view, fragment, tag).commit()

        }
    }
}