package me.leoprok.petzyandroid

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import me.leoprok.petzyandroid.parks.NewPark
import me.leoprok.petzyandroid.parks.ParkList
import me.leoprok.petzyandroid.util.FragmentMaker
import me.leoprok.petzyandroid.util.FragmentSwitcher
import com.google.android.gms.maps.MapFragment



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        //create new park list fragment
        FragmentMaker(this,ParkList(),"park_list",true)
    }
    //on back button pressed
    override fun onBackPressed() {
        //if the drawer open on back click close it else go back
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            //adds new park fragment
            R.id.new_park -> {
               FragmentMaker(this,NewPark(),"new_park",true)
            }
            R.id.parks_map -> {
                
            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        //close drawer
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    //once map fragment loads show park markers
    override fun onMapReady(map: GoogleMap) {
        //check for location permision if true set the map camera to current location
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            map.isMyLocationEnabled = true
        }else{
            //if false set the map to tell aviv latlng
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(32.109333, 34.855499), 13F))
        }
        map.addMarker(MarkerOptions().title("").snippet(""))
                //.position(sydney));
    }

}
