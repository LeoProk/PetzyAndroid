package tk.leopro.petzyandroid;

import android.app.Application;
import android.location.Location;

/**
 * Application class have volley rellated methods.
 */
public class AppController extends Application {

    public static String mFragmentTag = "news";
    public static Location currentLocation;


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
