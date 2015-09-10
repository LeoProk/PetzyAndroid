package tk.leopro.petzyandroid.AppSpecific;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import tk.leopro.petzyandroid.Interfaces.FactoryInterface;

/**
 * Factory for app specifics
 */
public class AppFactory {

    //Parsing info from html
    public static AsyncTask getHtmlInfo() {
        return new AdoptingHtmlParser().execute();
    }

    //Create list of park with data taken from parse database
    public static FactoryInterface getParksList(Context context, ListView listView) {
        return new ParksListMaker(context, listView);
    }

    //Build SQLite database using data from Parse
    public static FactoryInterface buildSQLParksData(Context context) {
        return new SqlParksBuilder(context);
    }
}
