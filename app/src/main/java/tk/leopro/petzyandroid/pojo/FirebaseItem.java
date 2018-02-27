package tk.leopro.petzyandroid.pojo;


import com.google.firebase.database.Exclude;

import tk.leopro.petzyandroid.AppController;

/**
 * fire base helper object for saving and retriving data from the server
 */
public class FirebaseItem implements Comparable {

    //make sure that our field names match the names of the properties
    // in the Firebase database
    // address of the park
    private String address;
    //title of the park
    private String title;
    //use that sumbited the park
    private String user;
    //image of the park
    private String image;
    //x and y of camera view
    private String camera;
    //location of the the park
    private Location location;
/*    //the distance of the item location and current location
    private int mDistance */

    public FirebaseItem(){
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    //constructor for new park (FirebaseItem)
    public FirebaseItem(String address, String title,String user
            , Location location,String image,String camera){
        this.address = address;
        this.title = title;
        this.user = user;
        this.location = location;
        this.image = image;
        this.camera = camera;
    }
    public String getAddress() {
        return address;
    }

    public String getCamera() {
        return camera;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

    public Location getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    //rounding the distance to park from current location and return it
    @Exclude
    public int getDistance() {
        android.location.Location parkLocation = new android.location.Location("Park Location");
        parkLocation.setLatitude(Double.parseDouble(location.getLat()));
        parkLocation.setLongitude(Double.parseDouble(location.getLng()));
        int distance = Math.round(AppController.sCurrentLocation.distanceTo(parkLocation));
        return distance;
    }

    //check the distance between the current location and the parks location
    // using getDistance() and return the final distance
    @Override
    public int compareTo(Object compare) {
        int comparedPark = ((FirebaseItem) compare).getDistance();
        int distance = getDistance();
        return distance - comparedPark;
    }

    //gets the image of the park using google maps api with lat and lng
    @Exclude
    public String getThumbnail(String postedBy){
        if(postedBy.equals("system_google_map")){
            return "https://maps.googleapis.com/maps/api/streetview?size=400x400&location="+location.getLat()+","+location.getLng() +
                    getCamera() + "&key=" + "AIzaSyDGTKCSCY_lpKtVrA1bJYctJdrJhjzGMlE";
        }else{
            return image;
        }
    }
}
