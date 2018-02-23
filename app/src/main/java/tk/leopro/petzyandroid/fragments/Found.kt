package tk.leopro.petzyandroid.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import tk.leopro.petzyandroid.R

/**
 * Created by Leo on 7/23/2015.
 */
class Found : Fragment() {

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                     savedInstanceState: Bundle): View {

        return inflater.inflate(R.layout.found_fragment, container, false)
    }
}
