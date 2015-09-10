package tk.leopro.petzyandroid.AppSpecific;

import android.location.Location;

import tk.leopro.petzyandroid.AppController;

/**
 * create new entery for list adapter
 */
public class Park implements Comparable {

    private String mTitle, mThumbnailUrl, mText, mLat, mLng;
    private int mParkDistance;

    //getters and setter for adapter
    public Park(String title, String thumbnailUrl, String text, String lat, String lng) {
        mTitle = title;
        mThumbnailUrl = thumbnailUrl;
        mText = text;
        mLat = lat;
        mLng = lng;
        mParkDistance = getDistance();
    }

    @Override
    public int compareTo(Object compare) {
        int comparedPark = ((Park) compare).getDistance();
        return this.mParkDistance - comparedPark;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getLength() {
        return distanceInKM(mParkDistance);
    }

    public String getText() {
        return mText;
    }

    @Override
    public String toString() {
        return mTitle + "\n" + mThumbnailUrl + "\n" + mText + "\n" + mLat + "\n" + mLng;
    }

    private int getDistance() {
        Location parkLocation = new Location("Park Location");
        parkLocation.setLatitude(Double.parseDouble(mLat));
        parkLocation.setLongitude(Double.parseDouble(mLng));
        int distance = Math.round(AppController.currentLocation.distanceTo(parkLocation));

        return distance;

    }

    private String distanceInKM(int parkDistance) {
        String range;
        if (parkDistance < 1000) {
            range = "meter";
        } else {
            range = "kilometre";
            parkDistance = parkDistance / 1000;
        }
        return String.valueOf(parkDistance) + " " + range;
    }
}