package tk.leopro.petzyandroid.AppSpecific;

/**
 * create new entery for list adapter
 */
public class Park implements Comparable {

    private String mTitle, mThumbnailUrl, mText, mLength;
    int mDistance;


    //getters and setter for adapter
    public Park(String title, String thumbnailUrl, String text, String length , int distance) {
        mTitle = title;
        mThumbnailUrl = thumbnailUrl;
        mText = text;
        mLength = length;
        mDistance = distance;

    }
    @Override
    public int compareTo(Object compare) {
        int comparedPark=((Park)compare).getDistance();
        return this.mDistance-comparedPark;
    }
    public String getTitle() {
        return mTitle;
    }

    public String getLength() {
        return mLength;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getText() {
        return mText;
    }
    public int getDistance(){
        return mDistance;
    }

}