package tk.leopro.petzyandroid.AppSpecific;

import android.content.Context;
import android.os.AsyncTask;

import tk.leopro.petzyandroid.Interfaces.FactoryInterface;

/**
 * Factory for app specifics
 */
public class AppFactory {

    //Parsing info from html
    public static AsyncTask getHtmlInfo(){
        return new AdoptingHtmlParser().execute();
    }
    //Create list of park with data taken from parse database
    public static FactoryInterface getParksList(){
        return new ParksListMaker();
    }
}
