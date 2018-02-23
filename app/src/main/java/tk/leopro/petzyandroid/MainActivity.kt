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

package tk.leopro.petzyandroid

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.Toast

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

import tk.leopro.petzyandroid.fragments.NewParkFragment
import tk.leopro.petzyandroid.fragments.ParksClosest
import tk.leopro.petzyandroid.interfaces.FactoryInterface
import tk.leopro.petzyandroid.main.AppFactory
import tk.leopro.petzyandroid.utilities.UtilitiesFactory

/**
 * Main class have navigator drawer and toolbar that it pass to fragment. And method to hide tabs or
 * to change tabs on click and names.
 */

class MainActivity : AppCompatActivity(), OnConnectionFailedListener, ConnectionCallbacks, LocationListener, FactoryInterface {


    private var mDrawerToggle: ActionBarDrawerToggle? = null

    private var mTabLayout: TabLayout? = null

    private var mLocationRequest: LocationRequest? = null

    private var mDrawerLayout: DrawerLayout? = null

    private var mGoogleApiClient: GoogleApiClient? = null

    private var mGso: GoogleSignInOptions? = null
    //popup that promp user to log in
    private var mLogInPopup: PopupWindow? = null
    //fire base auth instance
    private var mAuth: FirebaseAuth? = null
    //firebase log in listenner
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    //check if user logged in to firebase
    var userFirebaseLogin: Boolean = false
    //check if user picked address from address picked autocomplete
    private val mAddressPicked: Boolean = false

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create the toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        UtilitiesFactory.getToolbar(this, toolbar).doTask()
        //create the drawer
        mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val drawerList = findViewById(R.id.slider_list) as ListView
        mDrawerToggle = UtilitiesFactory.getDrawer(this, mDrawerLayout, drawerList, toolbar).doTask() as ActionBarDrawerToggle
        //Create tabs
        mTabLayout = findViewById(R.id.tabs) as TabLayout
        mTabLayout!!.setVisibility(View.GONE)
        //check if the user sign in to the app before
        mGso = null
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        mGso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        // Build a GoogleApiClient with access to the Google Sign-In API location API and the
        // options specified by gso.
        buildGoogleApi()
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = object : FirebaseAuth.AuthStateListener() {
            @Override
            fun onAuthStateChanged(@NonNull firebaseAuth: FirebaseAuth) {
                val user = firebaseAuth.getCurrentUser()
                if (user != null) {
                    userFirebaseLogin = true
                } else {
                    // User is signed out
                    Log.e("nay1", "onAuthStateChanged:signed_out")
                }
                // ...
            }
        }

        UtilitiesFactory.addFragment(this, ParksClosest(), "park", true).doTask()
        //AppFactory.buildSQLParksData(this).doTask();
    }


    @Override
    protected fun onPostCreate(savedInstanceState: Bundle) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle!!.syncState()
    }

    @Override
    fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle!!.onConfigurationChanged(newConfig)
    }


    @Override
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        return if (mDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    @Override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Log.e("error", result.getStatus().toString())
            val account = result.getSignInAccount()
            firebaseAuthWithGoogle(account)
            handleSignInResult(result)

        }

    }

    fun changeTabs(tabNames: Array<String>, tags: Array<String>) {
        UtilitiesFactory.createTabs(this, mTabLayout, tabNames, tags).doTask()
    }

    @Override
    fun onConnectionSuspended(i: Int) {
    }

    @Override
    fun onConnectionFailed(connectionResult: ConnectionResult) {
    }


    // check if sign-in succeeded
    private fun handleSignInResult(result: GoogleSignInResult) {
        Log.e("TAt", "handleSignInResult:" + result.isSuccess())

        if (result.isSuccess()) {
            //after successfully signing in save the user id
            val acct = result.getSignInAccount()
            UtilitiesFactory.saveFile(this, "user", acct.getId()).doTask()
            mLogInPopup!!.dismiss()
            UtilitiesFactory.addFragment(this, NewParkFragment(), "new", true).doTask()
        } else {
            Log.e("TRY", "error")
        }
    }

    @Override
    fun onConnected(bundle: Bundle) {
        getCurrentLocation()
        val lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient)
        if (lastLocation != null) {
            AppController.sCurrentLocation = Location(lastLocation)
        }
    }

    //check the current location
    private fun getCurrentLocation() {
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        mGoogleApiClient!!.disconnect()
    }

    //build the google api with place api and location api
    fun buildGoogleApi() {
        if (mGso == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addApi(LocationServices.API)
                    .build()
        } else {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, mGso)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addApi(LocationServices.API)
                    .build()
        }
    }

    @Override
    fun doTask(): Object? {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
        return null
    }

    //pop up with the google log in button
    fun googleLogInPopup() {
        mLogInPopup = AppFactory.getLogInPopup(mDrawerLayout, this, this@MainActivity, mGso).doTask() as PopupWindow
    }

    //used be the drawer when use click on create new item option in the list. connects to google api again
    fun googleApiConnect() {
        mGoogleApiClient!!.connect()
    }

    //saves the new location from listener to static location inside applicatin class
    @Override
    fun onLocationChanged(location: Location) {
        AppController.sCurrentLocation = Location(location)

    }

    @Override
    protected fun onStart() {
        super.onStart()
        if (mGoogleApiClient != null) {
            mGoogleApiClient!!.connect()
        }
        mAuth!!.addAuthStateListener(mAuthListener)
    }

    @Override
    protected fun onStop() {
        super.onStop()
        if (mGoogleApiClient != null && mGoogleApiClient!!.isConnected()) {
            mGoogleApiClient!!.disconnect()
        }
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("id", "firebaseAuthWithGoogle:" + acct.getId())

        val credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult>() {
                    @Override
                    fun onComplete(@NonNull task: Task<AuthResult>) {
                        Log.e("yay", "signInWithCredential:onComplete:" + task.isSuccessful())

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.e("yay", "signInWithCredential", task.getException())
                            Toast.makeText(this@MainActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
                })
    }

    companion object {
        //google log in intent code
        private val RC_SIGN_IN = 1000
    }


}
