package tk.leopro.petzyandroid

import android.app.Application
import android.location.Location
import android.text.TextUtils

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

import tk.leopro.petzyandroid.pojo.LruBitmapCache

/**
 * Application class have volley rellated methods.
 */
class AppController : Application() {
    //fragment tag control
    var fragmentTag: String? = null

    private var mRequestQueue: RequestQueue? = null

    private var mImageLoader: ImageLoader? = null

    val requestQueue: RequestQueue
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(getApplicationContext())
            }
            return mRequestQueue
        }

    val imageLoader: ImageLoader
        get() {
            requestQueue
            if (mImageLoader == null) {
                mImageLoader = ImageLoader(this.mRequestQueue,
                        LruBitmapCache())
            }
            return this.mImageLoader
        }

    @Override
    fun onCreate() {
        super.onCreate()
        //Firebase.setAndroidContext(this);
        instance = this
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String) {
        // set the default tag if tag is empty
        req.setTag(if (TextUtils.isEmpty(tag)) TAG else tag)
        requestQueue.add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.setTag(TAG)
        requestQueue.add(req)
    }

    fun cancelPendingRequests(tag: Object) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }

    companion object {

        val TAG = AppController::class.java!!.getSimpleName()
        //the current users location
        var sCurrentLocation: Location? = null
        //the instance of application class
        // code needed for volly api
        @get:Synchronized
        var instance: AppController? = null
            private set
    }
}
