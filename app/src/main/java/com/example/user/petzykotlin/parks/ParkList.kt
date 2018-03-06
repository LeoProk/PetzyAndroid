package com.example.user.petzykotlin.parks

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import me.leoprok.petzyandroid.R

/**
 * Show the closest  parks near you
 */

class ParkList : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.parks_list, container, false)
        //call list view and populate it
        val parkList = rootView.findViewById(R.id.parks) as ListView
        ParkMaker(activity, parkList)
        return rootView
    }
}
