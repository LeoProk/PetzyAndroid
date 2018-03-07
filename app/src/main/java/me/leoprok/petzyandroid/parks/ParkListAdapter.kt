package me.leoprok.petzyandroid.parks

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.android.volley.toolbox.ImageLoader
import me.leoprok.petzyandroid.Application
import me.leoprok.petzyandroid.R
import me.leoprok.petzyandroid.pojos.Park
import com.android.volley.toolbox.NetworkImageView
import android.widget.TextView





/**
 * Created by user on 3/1/18.
 */
class ParkListAdapter(var activity : Activity,var parkList : ArrayList<Park>): BaseAdapter() {

    private lateinit var inflater : LayoutInflater

    private lateinit var imageLoader : ImageLoader
    //get the park at current location
    override fun getItem(location: Int): Any {
        return parkList.get(location)
    }
    //get the item poistion
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    //get the list size with is the same size as park
    override fun getCount(): Int {
        return parkList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View
        //gets application
        val app = activity.applicationContext as Application
        //if the inflation is empty init it
        if (inflater.equals(null)){
            inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        //if the current row is empty create new list row
        if (convertView == null){
            view = inflater.inflate(R.layout.list_row,null)
        //otherwise reuse the current row
        }else{
            view = convertView
        }
        //if the current image loader is empty get it from application class
        if(imageLoader.equals(null)){
            imageLoader = app.imageLoader()
        }
        //gets the id of networkimage view for park location street view img
        val thumbNail = view.findViewById(R.id.thumbnail) as NetworkImageView
        //gets the id of park title
        val title = view.findViewById(R.id.title) as TextView
        //gets the id of park address
        val address = view.findViewById(R.id.address) as TextView
        //gets the id of distance between current location and park location
        val length = view.findViewById(R.id.length) as TextView
        //get the park of current list location
        val park = parkList.get(position)
        //set the image of the park
        thumbNail.setImageUrl(park.thumbnail(park.user),imageLoader)
        //set the title of park
        title.text = park.title
        //set the address of park
        address.text = park.address
        //set the distance of the park in km from the method
        length.text = distanceInKM(park.distance())

        // getting item data for the row
        return view
    }

    //return string distance in meter or kelometres
    private fun distanceInKM(parkDistance: Int): String {
        var parkDistance = parkDistance
        val range: String
        if (parkDistance < 1000) {
            range = "מטרים"
        } else {
            range = "קילומטרים"
            parkDistance = parkDistance / 1000
        }
        return parkDistance.toString() + " " + range
    }

}