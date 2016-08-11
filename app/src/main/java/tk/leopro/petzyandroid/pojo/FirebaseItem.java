package tk.leopro.petzyandroid.pojo;


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
    private Location location;
/*    //the distance of the item location and current location
    private int mDistance;*/

    public FirebaseItem(){
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    public FirebaseItem(String address, String title,String user
            , Location location,String image){
        this.address = address;
        this.title = title;
        this.user = user;
        this.location = location;
        this.image = image;
    }
    public String getAddress() {
        return address;
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


    public int calculateDistance() {
        android.location.Location parkLocation = new android.location.Location("Park Location");
        parkLocation.setLatitude(location.getLat());
        parkLocation.setLongitude(location.getLng());
        int distance = Math.round(AppController.sCurrentLocation.distanceTo(parkLocation));
        return distance;
    }


    @Override
    public int compareTo(Object compare) {
        int comparedPark = ((FirebaseItem) compare).calculateDistance();
        int distance = calculateDistance();
        return distance - comparedPark;
    }

}
