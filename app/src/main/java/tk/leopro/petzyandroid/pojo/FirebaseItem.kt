package tk.leopro.petzyandroid.pojo


import com.google.firebase.database.Exclude

import tk.leopro.petzyandroid.AppController

/**
 * fire base helper object for saving and retriving data from the server
 */
class FirebaseItem : Comparable {

    //make sure that our field names match the names of the properties
    // in the Firebase database
    // address of the park
    val address: String
    //title of the park
    val title: String
    //use that sumbited the park
    val user: String
    //image of the park
    val image: String
    //x and y of camera view
    val camera: String
    //location of the the park
    val location: Location

    //rounding the distance to park from current location and return it
    val distance: Int
        @Exclude
        get() {
            val parkLocation = android.location.Location("Park Location")
            parkLocation.setLatitude(Double.parseDouble(location.getLat()))
            parkLocation.setLongitude(Double.parseDouble(location.getLng()))
            return Math.round(AppController.sCurrentLocation.distanceTo(parkLocation))
        }
    /*    //the distance of the item location and current location
    private int mDistance;*/

    constructor() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    //constructor for new park (FirebaseItem)
    constructor(address: String, title: String, user: String, location: Location, image: String, camera: String) {
        this.address = address
        this.title = title
        this.user = user
        this.location = location
        this.image = image
        this.camera = camera
    }

    //check the distance between the current location and the parks location
    // using getDistance() and return the final distance
    @Override
    operator fun compareTo(compare: Object): Int {
        val comparedPark = (compare as FirebaseItem).distance
        val distance = distance
        return distance - comparedPark
    }

    //gets the image of the park using google maps api with lat and lng
    @Exclude
    fun getThumbnail(postedBy: String): String {
        return if (postedBy.equals("system_google_map")) {
            "https://maps.googleapis.com/maps/api/streetview?size=400x400&location=" + location.getLat() + "," + location.getLng() +
                    camera + "&key=" + "AIzaSyDGTKCSCY_lpKtVrA1bJYctJdrJhjzGMlE"
        } else {
            image
        }
    }
}
