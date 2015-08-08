package tk.leopro.petzyandroid.AppSpecific;

/**
 * create new entery for list adapter
 */
public class Park {

    private String mTitle, mThumbnailUrl, mText, mLength;

    //getters and setter for adapter
    public Park(String title, String thumbnailUrl, String text, String length) {
        mTitle = title;
        mThumbnailUrl = thumbnailUrl;
        mText = text;
        mLength = length;

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

}