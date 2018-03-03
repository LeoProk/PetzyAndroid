package me.leoprok.petzyandroid.parks

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.android.volley.toolbox.ImageLoader
import me.leoprok.petzyandroid.R
import me.leoprok.petzyandroid.pojos.Park

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
            imageLoader = com.example.user.petzykotlin.Application.sInstance.
        }
        return view
    }


}