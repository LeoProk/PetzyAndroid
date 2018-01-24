package tk.leopro.petzyandroid.pojo;

/**
 * create new entery for list adapter
 * still not in use
 */
public class Pet {

    private String mTitle, mThumbnailUrl, mText, mDate;

    //getters and setter for adapter
    public Pet(String title, String thumbnailUrl, String text, String date) {
        mTitle = title;
        mThumbnailUrl = thumbnailUrl;
        mText = text;
        mDate = date;

    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }


    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }


    public String getText() {
        return mText;
    }


}