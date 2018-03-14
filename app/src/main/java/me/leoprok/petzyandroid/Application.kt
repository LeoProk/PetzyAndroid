package me.leoprok.petzyandroid


/**
 * Application class have volley rellated methods.
 * current fragment tag
 * current location
 */

import android.app.Application
import android.location.Location
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import me.leoprok.petzyandroid.pojos.LruBitmapCache

class Application : Application() {
    //tag of the current fragment
    var fragTag = ""
    //val TAG =  Application::class.simpleName
    val TAG = "try"
    //current location of user
    lateinit var currentLocation:Location
    //tag used to id frags
    lateinit var fragmentTag:String
    //request for volley
    private lateinit var mRequestQueue: RequestQueue
    //load loader for volley
    private lateinit var mImageLoader: ImageLoader
    //the instance of application class
//    companion object {
//        lateinit var sInstance : me.leoprok.petzyandroid
//        .Application
//    }
    //get current location onCreate
    override fun onCreate() {
        super.onCreate()
        //sInstance = this
        getCurrentLocation()
    }
    //checks if request null if yes makes new volley request
    private fun requestQueue(): RequestQueue{
        if (mRequestQueue.equals(null)) {
            mRequestQueue = Volley.newRequestQueue(applicationContext)
        }
        return mRequestQueue
    }
    fun imageLoader() : ImageLoader{
        requestQueue()
        if (mImageLoader.equals(null)){
            mImageLoader = ImageLoader(this.mRequestQueue , LruBitmapCache())
        }
        return mImageLoader
    }


    private fun getCurrentLocation(){

    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.setTag(TAG)
        requestQueue().add(req)
    }

    fun cancelPendingRequests(tag: Any) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag)
        }
    }
}
