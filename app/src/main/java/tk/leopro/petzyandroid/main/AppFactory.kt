package tk.leopro.petzyandroid.main

import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ListView

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.maps.GoogleMap
import tk.leopro.petzyandroid.interfaces.FactoryInterface
import tk.leopro.petzyandroid.pojo.FirebaseItem

/**
 * Factory for app specifics
 */
object AppFactory {

    //Parsing info from html
    val htmlInfo: AsyncTask
        get() = AdoptingHtmlParser().execute()

    //Create list of park with data taken from parse database
    fun getParksListMaker(context: Context, listView: ListView): FactoryInterface {
        return ParkListMaker(context, listView)
    }

    //Build SQLite database using data from Parse
    fun buildSQLParksData(context: Context): FactoryInterface {
        return SqlParksBuilder(context)
    }

    //Create markers on google maps
    fun createMarkers(googleMap: GoogleMap, context: Context): FactoryInterface {
        return ParkMapMaker(googleMap, context)
    }

    // show pop up message when title text view is missing
    fun titlePopUp(view: View, context: Context): FactoryInterface {
        return PopUpGenerator(view, context, "title")
    }

    // show pop up message when address text view is missing
    fun addressPopUp(view: View, context: Context): FactoryInterface {
        return PopUpGenerator(view, context, "address")
    }

    // show pop up message when phone text view is missing
    fun phonePopUp(view: View, context: Context): FactoryInterface {
        return PopUpGenerator(view, context, "phone")
    }

    // show pop up message when info text view is missing
    fun infoPopUp(view: View, context: Context): FactoryInterface {
        return PopUpGenerator(view, context, "info")
    }

    //gets data from firebase server
    fun getLogInPopup(anchorView: View, context: Context, factoryInterface: FactoryInterface,
                      gso: GoogleSignInOptions): FactoryInterface {
        return LogInPopup(anchorView, context, factoryInterface, gso)
    }

    // create new fire base entry
    fun saveNewPark(newItem: FirebaseItem): FactoryInterface {
        return SaveNewPark(newItem)
    }

    //Call the loading pop up.
    fun getLoadingPopup(context: Context, anchorView: View, inputCondition: String): FactoryInterface {
        return LoadingPopup(anchorView, context, inputCondition)
    }
}
