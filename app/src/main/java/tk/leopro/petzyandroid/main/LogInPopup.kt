package tk.leopro.petzyandroid.main

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton

import tk.leopro.petzyandroid.R
import tk.leopro.petzyandroid.interfaces.FactoryInterface

/**
 * this class create popup with google and facebook login
 */
internal class LogInPopup(private val mAnchorView: View, private val mContext: Context, private val mFactoryInterface: FactoryInterface,
                          private val mGso: GoogleSignInOptions) : FactoryInterface {

    private var mInflater: LayoutInflater? = null

    @Override
    fun doTask(): Object {
        // Sets the right layout to the view
        mInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //create new pop up
        val popupView = mInflater!!.inflate(R.layout.login_popup, null)
        val popupWindow = PopupWindow(popupView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        //google sign in button
        val signInButton = popupView.findViewById(R.id.sign_in_button) as SignInButton
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signInButton.setScopes(mGso.getScopeArray())
        signInButton.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                //starts log in session on sign in button click
                mFactoryInterface.doTask()
            }
        })
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
