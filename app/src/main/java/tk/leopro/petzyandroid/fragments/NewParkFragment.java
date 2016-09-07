package tk.leopro.petzyandroid.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.MainActivity;
import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.interfaces.RequestPlaceInterface;
import tk.leopro.petzyandroid.main.AppFactory;
import tk.leopro.petzyandroid.pojo.FirebaseItem;
import tk.leopro.petzyandroid.pojo.GooglePredictionData;
import tk.leopro.petzyandroid.utilities.UtilitiesFactory;

/**
 * this app create new item in firebase database with the folowing params
 * subject : picked from subject spinner cant be default
 * date : 2date taken from website to avoid wrong date on devices
 * text : input from the user about the item
 * address : autocompletet text view that uses google places prediction to show street
 * address to the user that he must chose from
 * phone : phone of the user
 * title : title for the item by user
 * user :
 */
public class NewParkFragment extends Fragment {

    private EditText mTitle;
    //auto comeplete address
    private AutoCompleteTextView mAddress;
    //location of the chosen street address
    private tk.leopro.petzyandroid.pojo.Location mChosenLocation;
    //subscribtion for google place prediction
    private Subscription mSubscription;
    //patch to the user picked iamge location
    private Uri mImagePath;
    //imageview of user image input
    private ImageView mImageView;
    //request code for gallery intent
    private static final int REQUEST_GALLERY = 1;
    //request code for camera intent
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    //check if user picked picture
    private boolean mUserImage;
    //check if user picked address
    private boolean mUserAddress;
    //popup displaing that the user park is saving
    private PopupWindow mSavePopup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_new_park, container, false);
        //get the application class
        final AppController appController = (AppController) getActivity().getApplicationContext();
        //initializes views
        mTitle = (EditText) rootView.findViewById(R.id.title);
        mAddress  = (AutoCompleteTextView) rootView.findViewById(R.id.address);
        //lang lat array of predictions
        final ArrayList<tk.leopro.petzyandroid.pojo.Location> predictedLocation = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, new String[]{});
        // let the user to input image for park on click
        mImageView = (ImageView) rootView.findViewById(R.id.image);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        mAddress.setAdapter(arrayAdapter);
        mAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mUserAddress = true;
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
                                ArrayList<String> temp = new ArrayList<>();
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
                    if (mAddress.getText().toString().isEmpty() && mUserAddress == false) {
                        AppFactory.addressPopUp(mTitle, getActivity()).doTask();
                    } else {
                        if(mUserImage == false){
                            AppFactory.infoPopUp(mTitle,getActivity()).doTask();
                        }else {
                            //calls loading popup
                            mSavePopup =(PopupWindow) AppFactory.getLoadingPopup(getActivity(),mTitle,"save").doTask();
                            //upload image to database
                            uploadImage();
                        }
                    }
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY || requestCode == REQUEST_GALLERY  ) {
            if (data == null) {
                //Display an error
            }else {
                try {
                    mUserImage = true;
                    InputStream inputStream =getActivity().getContentResolver().openInputStream(data.getData());
                    mImageView.setImageBitmap(BitmapFactory.decodeStream(new BufferedInputStream(inputStream)));
                    mImagePath = data.getData();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //call the image gallery
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }
    //calls the camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //upload the image to firebase database
    public void uploadImage(){
        try {
            InputStream inputStream =getActivity().getContentResolver().openInputStream(mImagePath);
            //upload the input stream we get from user choice to firebase storage and saves the url to
            // firebase object
            final StorageReference storageRef = FirebaseStorage.getInstance()
                    .getReferenceFromUrl("gs://petzy-1001.appspot.com");
            StorageReference mountainsRef = storageRef.child(UUID.randomUUID().toString());

            final UploadTask uploadTask = mountainsRef.putStream(inputStream);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("imageUpload error",e.toString());
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //save to firebase after creating hashmap of the new items array list
                    FirebaseItem itemForSave = new FirebaseItem(mAddress.getText().toString()
                            ,mTitle.getText().toString(),(String) UtilitiesFactory.getFile(getActivity(), "user").doTask()
                            ,mChosenLocation ,taskSnapshot.getDownloadUrl().toString(),"user");
                    AppFactory.saveNewPark(itemForSave).doTask();
                    //remove wating for save popup
                    mSavePopup.dismiss();
                    //remove fragment when done
                    UtilitiesFactory.removeFragment(getActivity()).doTask();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
