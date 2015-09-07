package tk.leopro.petzyandroid.AppSpecific;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.Fragments.CustomListAdapter;
import tk.leopro.petzyandroid.Interfaces.FactoryInterface;
import tk.leopro.petzyandroid.Utilities.UtilitiesFactory;

/**
 * Retrieve data from sql and put it in list view adapter
 */
final class ParksListMaker implements FactoryInterface{
    private Context mContext;
    private ListView mListView;

    public ParksListMaker(Context context,ListView listView){
        mContext = context;
        mListView = listView;

    }

    @Override
    public Object doTask() {

        List<Park> parksList = new ArrayList();
        UtilitiesFactory.callSQL(mContext,parksList,"retrieve").doTask();
        Log.e("YAY",parksList.get(2).toString());
        Collections.sort(parksList);
        FragmentActivity activity = (FragmentActivity) mContext;
        CustomListAdapter adapter = new CustomListAdapter(activity, parksList);
        mListView.setAdapter(adapter);
            return null;


    }
}
