package tk.leopro.petzyandroid.main

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView

import tk.leopro.petzyandroid.AppController
import tk.leopro.petzyandroid.R
import tk.leopro.petzyandroid.interfaces.FactoryInterface

/**
 * creates new popup for missing fields depends on the String inputConidtion
 */
internal class PopUpGenerator//constractor for new popup
(private val mAnchorView: View, private val mContext: Context, private val mInputCondition: String) : FactoryInterface {

    private var mInflater: LayoutInflater? = null
    @Override
    fun doTask(): Object {
        // Sets the right layout to the view
        mInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = mInflater!!.inflate(R.layout.popup_generator_fragment, null)
        //gets the application class
        val appController = mContext.getApplicationContext() as AppController
        val popupWindow = PopupWindow(popupView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        //text view of popup text text view
        val textView = popupView.findViewById(R.id.message) as TextView
        //button that dismiss the pop up on click
        val submit = popupView.findViewById(R.id.submit) as Button
        submit.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(view: View) {
                popupWindow.dismiss()
            }
        })
        textView.setTypeface(null, Typeface.BOLD)
        //change load message bassed on input
        when (mInputCondition) {
        //pop up when the title is missing
            "title" -> textView.setText(mContext.getResources().getString(R.string.wrong_title))
        //pop up when the address is missing
            "address" -> textView.setText(mContext.getResources().getString(R.string.wrong_address))
        //pop up when the phone is missing
            "phone" -> textView.setText(mContext.getResources().getString(R.string.wrong_phone))
        //pop up when the image is missing
            "info" -> textView.setText(mContext.getResources().getString(R.string.wrong_info))
        //pop up when the category is missing
            "subject" -> textView.setText(mContext.getResources().getString(R.string.wrong_subject))
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
