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

    override fun getItem(location: Int): Any {
        return parkList.get(location)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return parkList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View
        if (inflater.equals(null)){
            inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertView == null){
            view = inflater.inflate(R.layout.list_row,null)

        }else{
            view = convertView
        }
        if(imageLoader.equals(null)){
            imageLoader = Application.sInstance.imageLoader()
        }
        val thumbNail = view.findViewById(R.id.thumbnail) as NetworkImageView
        val title = view.findViewById(R.id.title) as TextView
        val address = view.findViewById(R.id.address) as TextView
        val length = view.findViewById(R.id.length) as TextView
        val park = parkList.get(position)
        thumbNail.setImageUrl(park.thumbnail(park.user),imageLoader)
        title.text = park.title
        address.text = park.address
        length.setText(distanceInKM(park.distance()))

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