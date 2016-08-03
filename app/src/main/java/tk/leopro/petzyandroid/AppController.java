package tk.leopro.petzyandroid;

import android.app.Application;
import android.location.Location;

import com.firebase.client.Firebase;

/**
 * Application class have volley rellated methods.
 */
public class AppController extends Application {

    //fragment tag control
    public String fragmentTag;
    //the current users location
    public static Location sCurrentLocation;
    //the timestamp of selected date in new item fragment
    public String timestamp;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
