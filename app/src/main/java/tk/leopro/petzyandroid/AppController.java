package tk.leopro.petzyandroid;

import android.app.Application;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;

import tk.leopro.petzyandroid.VolleyApi.LruBitmapCache;

/**
 * Application class have volley rellated methods.
 */
public class AppController extends Application {

    public static String mFragmentTag = "news";

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "NGE1Ln6TlSWeJ5HVh7dR0wC6azlNIiS1pUZAmV33", "UM7GpKFElo7dCeFWjpSdJoHi32pxjclG4rokbwg9");
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
