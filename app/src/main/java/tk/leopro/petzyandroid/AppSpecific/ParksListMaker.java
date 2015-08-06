package tk.leopro.petzyandroid.AppSpecific;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import tk.leopro.petzyandroid.Interfaces.FactoryInterface;

/**
 * Created by Leo on 8/6/2015.
 */
final class ParksListMaker implements FactoryInterface{
    @Override
    public Object doTask() {
        Log.e("aya", "g24124141gggggggggg");
        final ArrayList<Park> parksList = new ArrayList<>();
        final ParseQuery query = ParseQuery.getQuery("Parks");
        query.getInBackground("TkIPJCNlqD", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    Log.e("aya","YAY");
                    JSONArray myArray = object.getJSONArray("parks");
                    for (int i = 0; i < myArray.length() ; i++) {
                        try {
                         String[] finalParks =  myArray.get(i).toString().split("Break:");
                            Log.e("aya",myArray.get(i).toString());
                         parksList.add(new Park(finalParks[0],finalParks[1],finalParks[2]));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            Log.e("aya", "gwagagggggggggggggggg");
                        }
                    }
                    
                } else {


                }
            }
        });
        return null;
    }
}
