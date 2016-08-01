package tk.leopro.petzyandroid;

import android.app.Application;
import android.location.Location;

import com.firebase.client.Firebase;

/**
 * Application class have volley rellated methods.
 */
public class AppController extends Application {

    public static String fragmentTag = "news";
    public static Location currentLocation;


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
