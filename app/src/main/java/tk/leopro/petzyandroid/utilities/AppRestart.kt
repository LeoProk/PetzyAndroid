package tk.leopro.petzyandroid.utilities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

import tk.leopro.petzyandroid.MainActivity
import tk.leopro.petzyandroid.interfaces.FactoryInterface

/**
 * quite the app and launch it agin
 */
internal class AppRestart(var mContext: Context) : FactoryInterface {

    @Override
    fun doTask(): Object? {
        val mStartActivity = Intent(mContext, MainActivity::class.java)
        val mPendingIntentId = 123456
        val mPendingIntent = PendingIntent.getActivity(mContext, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT)
        val mgr = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
        System.exit(0)
        return null
    }
}
