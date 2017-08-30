package tk.leopro.petzyandroid.main;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;

/**
 * hold the diffrent pop up for missing field in new item create fragment
 */
final class PopUpGenerator implements FactoryInterface {

    private LayoutInflater mInflater;

    private View mAnchorView;

    private Context mContext;

    private String mInputCondition;

    public PopUpGenerator(View anchorView, Context context, String inputCondition) {
        mAnchorView = anchorView;
        mContext = context;
        mInputCondition = inputCondition;
    }
    @Override
    public Object doTask() {
        // Sets the right layout to the view
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = mInflater.inflate(R.layout.popup_generator_fragment, null);
        //gets the application class
        final AppController appController = (AppController) mContext.getApplicationContext();
        final PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final TextView textView = (TextView) popupView.findViewById(R.id.message);
        final Button submit = (Button) popupView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        textView.setTypeface(null, Typeface.BOLD);
        //change load message bassed on input
        switch (mInputCondition) {
            case "title":
                textView.setText(mContext.getResources().getString(R.string.wrong_title));
                break;
            case "address":
                textView.setText(mContext.getResources().getString(R.string.wrong_address));
                break;
            case "phone":
                textView.setText(mContext.getResources().getString(R.string.wrong_phone));
                break;
            case "info":
                textView.setText(mContext.getResources().getString(R.string.wrong_info));
                break;
            case "subject":
                textView.setText(mContext.getResources().getString(R.string.wrong_subject));
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
