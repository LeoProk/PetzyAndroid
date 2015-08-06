package tk.leopro.petzyandroid.AppSpecific;

/**
 * create new entery for list adapter
 */
public class Park {

    private String mTitle,  mText, mLength;

    //getters and setter for adapter
    public Park(String title, String text, String length) {
        mTitle = title;
        mText = text;
        mLength = length;

    }

    public String getTitle() {
        return mTitle;
    }

    public String getLength() {
        return mLength;
    }

    public String getText() {
        return mText;
    }

}