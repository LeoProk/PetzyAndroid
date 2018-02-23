package tk.leopro.petzyandroid.main

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView

import tk.leopro.petzyandroid.AppController
import tk.leopro.petzyandroid.R
import tk.leopro.petzyandroid.interfaces.FactoryInterface

/**
 * This class show popup if he user trying to quit without saving
 */
internal class LoadingPopup(private val mAnchorView: View, private val mContext: Context, private val mInputCondition: String) : FactoryInterface {

    private var mInflater: LayoutInflater? = null

    @Override
    fun doTask(): Object {
        // Sets the right layout to the view
        mInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = mInflater!!.inflate(R.layout.loading_popup, null)
        //gets the application class
        val appController = mContext.getApplicationContext() as AppController
        val popupWindow = PopupWindow(popupView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        val textView = popupView.findViewById(R.id.message) as TextView
        textView.setTypeface(null, Typeface.BOLD)
        //change load message bassed on input
        when (mInputCondition) {
            "save" -> textView.setText(mContext.getResources().getString(R.string.save))
            else -> {
            }
        }
        // Initialize more widgets from `popup_layout.xml`
        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true)

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(ColorDrawable())

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(mAnchorView, Gravity.CENTER,
                0, 0)

        return popupWindow
    }

}
