package tk.leopro.petzyandroid.pojo;


import com.google.firebase.database.Exclude;

import tk.leopro.petzyandroid.AppController;

/**
 * fire base helper object for saving and retriving data from the server
 */
public class FirebaseItem implements Comparable {

    //make sure that our field names match the names of the properties
    // in the Firebase database
    private String address;
    private String title;
    private String user;
    private String image;
    private String camera;
    private Location location;
/*    //the distance of the item location and current location
    private int mDistance;*/

    public FirebaseItem(){
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
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

    @Exclude
    public int getDistance() {
        android.location.Location parkLocation = new android.location.Location("Park Location");
        parkLocation.setLatitude(Double.parseDouble(location.getLat()));
        parkLocation.setLongitude(Double.parseDouble(location.getLng()));
        int distance = Math.round(AppController.sCurrentLocation.distanceTo(parkLocation));
        return distance;
    }


    @Override
    public int compareTo(Object compare) {
        int comparedPark = ((FirebaseItem) compare).getDistance();
        int distance = getDistance();
        return distance - comparedPark;
    }

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
