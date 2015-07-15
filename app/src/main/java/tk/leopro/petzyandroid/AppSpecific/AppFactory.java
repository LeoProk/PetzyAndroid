package tk.leopro.petzyandroid.AppSpecific;

import android.content.Context;
import android.os.AsyncTask;

import tk.leopro.petzyandroid.Interfaces.FactoryInterface;

/**
 * Created by Leo on 7/13/2015.
 */
public class AppFactory {

    //Parsing info from html
    public static AsyncTask getHtmlInfo(){
        return new AdoptingHtmlParser().execute();
    }
}
