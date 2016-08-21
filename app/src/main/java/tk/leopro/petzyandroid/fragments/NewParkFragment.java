package tk.leopro.petzyandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.interfaces.RequestPlaceInterface;
import tk.leopro.petzyandroid.main.AppFactory;
import tk.leopro.petzyandroid.pojo.FirebaseItem;
import tk.leopro.petzyandroid.pojo.GooglePredictionData;
import tk.leopro.petzyandroid.utilities.UtilitiesFactory;

/**
 * this app create new item in firebase database with the folowing params
 * @subject picked from subject spinner cant be default
 * @date 2date taken from website to avoid wrong date on devices
 * @text input from the user about the item
 * @address autocompletet text view that uses google places prediction to show street
 * address to the user that he must chose from
 * @phone phone of the user
 * @title title for the item by user
 * @user
 */
public class NewParkFragment extends Fragment {

    private EditText mTitle;
    //auto comeplete address
    private AutoCompleteTextView mAddress;
    //location of the chosen street address
    private tk.leopro.petzyandroid.pojo.Location mChosenLocation;
    //subscribtion for google place prediction
    private Subscription mSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_new_item, container, false);
        //get the application class
        final AppController appController = (AppController) getActivity().getApplicationContext();
        //initializes views
        mTitle = (EditText) rootView.findViewById(R.id.title);
        mAddress  = (AutoCompleteTextView) rootView.findViewById(R.id.address);
        //lang lat array of predictions
        final ArrayList<tk.leopro.petzyandroid.pojo.Location> predictedLocation = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, new String[]{});
        mAddress.setAdapter(arrayAdapter);
        mAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mChosenLocation = predictedLocation.get(position);
            }
        });
        //get info from google place prediction using rxandroid and retrofit
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/textsearch/")
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mSubscription != null) {
                    if (!mSubscription.isUnsubscribed()) {
                        mSubscription.unsubscribe();
                    }
                }
                RequestPlaceInterface requestPlace = retrofit.create(RequestPlaceInterface.class);
                final Observable<GooglePredictionData> call = requestPlace.getJSON(editable.toString()
                        , 31.977682 + "," + 34.764381,
                        "5000", "iw", "AIzaSyDGTKCSCY_lpKtVrA1bJYctJdrJhjzGMlE");
                mSubscription = call
                        .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<GooglePredictionData>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GooglePredictionData googlePredictionData) {
                                predictedLocation.clear();
                                ArrayList<String> temp = new ArrayList<String>();
                                //loops true all the prediction
                                for (int i = 0; i < googlePredictionData.getResults().size(); i++) {
                                    temp.add(googlePredictionData.getResults().get(i).getFormattedAddress());
                                    predictedLocation.add(googlePredictionData.getResults().get(i).getGeometry().getLocation());
                                }
                                String[] data = temp.toArray(new String[temp.size()]);
                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
                                mAddress.setAdapter(arrayAdapter);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        });
            }
        });
        final Button submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if all field are full if yes create new parse item and goes back to last window
                if (mTitle.getText().toString().isEmpty()) {
                    AppFactory.titlePopUp(mTitle, getActivity()).doTask();
                } else {
                    if (mAddress.getText().toString().isEmpty()) {
                        AppFactory.addressPopUp(mTitle, getActivity()).doTask();
                    } else {
                        //change fragment
                        //save to firebase after creating hashmap of the new items array list
                        FirebaseItem itemForSave = new FirebaseItem(mAddress.getText().toString()
                                ,mTitle.getText().toString(),(String) UtilitiesFactory.getFile(getActivity(), "user").doTask(),mChosenLocation ,"null");
                        AppFactory.saveNewPark(itemForSave).doTask();
                        UtilitiesFactory.removeFragment(getActivity()).doTask();
                    }
                }
            }
        });
        return rootView;
    }
}
