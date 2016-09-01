/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.leopro.petzyandroid;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import tk.leopro.petzyandroid.fragments.NewParkFragment;
import tk.leopro.petzyandroid.fragments.ParksClosest;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.main.AppFactory;
import tk.leopro.petzyandroid.utilities.UtilitiesFactory;

/**
 * Main class have navigator drawer and toolbar that it pass to fragment. And method to hide tabs or
 * to change tabs on click and names.
 */

public class MainActivity extends AppCompatActivity  implements OnConnectionFailedListener,ConnectionCallbacks
        ,LocationListener,FactoryInterface {


    private ActionBarDrawerToggle mDrawerToggle;

    private TabLayout mTabLayout;

    private LocationRequest mLocationRequest;

    private DrawerLayout mDrawerLayout;

    private GoogleApiClient mGoogleApiClient;

    private GoogleSignInOptions mGso;

    private PopupWindow mLogInPopup;

    private String mImagePath;

    private static final int RC_SIGN_IN = 1000;

    private static final int REQUEST_GALLERY = 1;

    static final int REQUEST_IMAGE_CAPTURE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create the toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        UtilitiesFactory.getToolbar(this, toolbar).doTask();
        //create the drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ListView drawerList = (ListView) findViewById(R.id.slider_list);
        mDrawerToggle = (ActionBarDrawerToggle) UtilitiesFactory.getDrawer(this, mDrawerLayout, drawerList, toolbar).doTask();
        //Create tabs
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setVisibility(View.GONE);
        //check if the user sign in to the app before
        mGso = null;
        if(((String)UtilitiesFactory.getFile(this,"user").doTask()).isEmpty()) {
            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .build();
            // Build a GoogleApiClient with access to the Google Sign-In API location API and the
            // options specified by gso.
        }
        buildGoogleApi();
        UtilitiesFactory.addFragment(this,new ParksClosest(),"park",true).doTask();
        //AppFactory.buildSQLParksData(this).doTask();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.e("error", result.getStatus().toString());
            handleSignInResult(result);
        }
        if (requestCode == REQUEST_GALLERY || requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }else {
                mImagePath = data.getData().getPath();
            }
        }
    }
    public void changeTabs(String[] tabNames, String[] tags) {
        UtilitiesFactory.createTabs(this, mTabLayout, tabNames, tags).doTask();
    }
    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    // check if sign-in succeeded
    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("TAt", "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            //after successfully signing in save the user id
            GoogleSignInAccount acct = result.getSignInAccount();
            UtilitiesFactory.saveFile(this, "user", acct.getId()).doTask();
            mLogInPopup.dismiss();
            UtilitiesFactory.addFragment(this, new NewParkFragment(),"new", true).doTask();
            Log.e("TRY",acct.getId());
        } else {
            Log.e("TRY","error");
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (lastLocation != null) {
            AppController.sCurrentLocation = new Location(lastLocation);
        }
        UtilitiesFactory.addFragment(this,new ParksClosest(),"news",true).doTask();
    }
    //check the current location
    private void getCurrentLocation(){
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mGoogleApiClient.disconnect();
    }

    //build the google api with place api and location api
    public void buildGoogleApi(){
        if(mGso==null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addApi(LocationServices.API)
                    .build();
        }else {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, mGso)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addApi(LocationServices.API)
                    .build();
        }
    }
    @Override
    public Object doTask() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        return null;
    }
    //pop up with the google log in button
    public void googleLogInPopup(){
        mLogInPopup = (PopupWindow)AppFactory.getLogInPopup(mDrawerLayout,this,MainActivity.this,mGso).doTask();
    }

    //used be the drawer when use click on create new item option in the list. connects to google api again
    public void googleApiConnect(){
        mGoogleApiClient.connect();
    }

    //saves the new location from listener to static location inside applicatin class
    @Override
    public void onLocationChanged(Location location) {
        AppController.sCurrentLocation = new Location(location);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if( mGoogleApiClient != null )
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
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
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //upload the image to firebase database
    public void uploadImage(){
        try {
            final InputStream inputStream = new FileInputStream(new File(mImagePath));
            //upload the input stream we get from user choice to firebase storage and saves the url to
            // firebase object
            final StorageReference storageRef = FirebaseStorage.getInstance()
                    .getReferenceFromUrl("gs://petzy-1001.appspot.com");
            final UploadTask uploadTask = storageRef.putStream(inputStream);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final AppController appController = (AppController) getApplicationContext();
                    appController.imageUrl = taskSnapshot.getDownloadUrl().toString();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
