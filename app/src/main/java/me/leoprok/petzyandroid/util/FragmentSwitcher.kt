package me.leoprok.petzyandroid.util

import android.content.Context
import android.support.v7.app.AppCompatActivity
import me.leoprok.petzyandroid.Application

/**
 * Switches between 2 fragments
 */

class FragmentSwitcher(var context: Context,newTag:String){

    init {
        val appController = context.getApplicationContext() as Application
        val activity = context as AppCompatActivity
        val ft = activity.fragmentManager.beginTransaction()
        ft.remove(activity.fragmentManager.findFragmentByTag(appController.fragmentTag))
        appController.fragmentTag = newTag
        ft.show(activity.fragmentManager.findFragmentByTag(newTag)).commit()
    }
}