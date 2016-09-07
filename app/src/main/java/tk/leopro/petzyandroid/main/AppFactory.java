package tk.leopro.petzyandroid.main;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.GoogleMap;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.pojo.FirebaseItem;

/**
 * Factory for app specifics
 */
public class AppFactory {

    //Parsing info from html
    public static AsyncTask getHtmlInfo() {
        return new AdoptingHtmlParser().execute();
    }

    //Create list of park with data taken from parse database
    public static FactoryInterface getParksListMaker(Context context, ListView listView) {
        return new ParkListMaker(context, listView);
    }

    //Build SQLite database using data from Parse
    public static FactoryInterface buildSQLParksData(Context context) {
        return new SqlParksBuilder(context);
    }
    //Create markers on google maps
    public static FactoryInterface createMarkers(GoogleMap googleMap,Context context) {
        return new ParkMapMaker(googleMap,context);
    }
    // show pop up message when title text view is missing
    public static FactoryInterface titlePopUp(View view ,Context context) {
        return new PopUpGenerator(view,context,"title");
    }
    // show pop up message when address text view is missing
    public static FactoryInterface addressPopUp(View view ,Context context) {
        return new PopUpGenerator(view,context,"address");
    }
    // show pop up message when phone text view is missing
    public static FactoryInterface phonePopUp(View view ,Context context) {
        return new PopUpGenerator(view,context,"phone");
    }
    // show pop up message when info text view is missing
    public static FactoryInterface infoPopUp(View view ,Context context) {
        return new PopUpGenerator(view,context,"info");
    }
    //gets data from firebase server
    public static FactoryInterface getLogInPopup(View anchorView, Context context,FactoryInterface factoryInterface,
                                                 GoogleSignInOptions gso){
        return new LogInPopup(anchorView,context,factoryInterface,gso);
    }
    // create new fire base entry
    public static FactoryInterface saveNewPark(FirebaseItem newItem){
        return new SaveNewPark(newItem);
    }
    //Call the loading pop up.
    public static FactoryInterface getLoadingPopup(Context context, View anchorView, String inputCondition) {
        return new LoadingPopup(anchorView, context, inputCondition);
    }
}
