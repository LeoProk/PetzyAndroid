package tk.leopro.petzyandroid.main;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;

/**
 * This class show popup if he user trying to quit without saving
 */
final class LoadingPopup implements FactoryInterface {

    private LayoutInflater mInflater;

    private View mAnchorView;

    private Context mContext;

    private String mInputCondition;

    public LoadingPopup(View anchorView, Context context, String inputCondition) {
        mAnchorView = anchorView;
        mContext = context;
        mInputCondition = inputCondition;
    }

    @Override
    public Object doTask() {
        // Sets the right layout to the view
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = mInflater.inflate(R.layout.loading_popup, null);
        //gets the application class
        final AppController appController = (AppController) mContext.getApplicationContext();
        final PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final TextView textView = (TextView) popupView.findViewById(R.id.message);
        textView.setTypeface(null, Typeface.BOLD);
        //change load message bassed on input
        switch (mInputCondition) {
            case "save":
                textView.setText(mContext.getResources().getString(R.string.save));
                break;
            default:
                break;
        }
        // Initialize more widgets from `popup_layout.xml`
        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(mAnchorView, Gravity.CENTER,
                0, 0);

        return popupWindow;
    }

}
