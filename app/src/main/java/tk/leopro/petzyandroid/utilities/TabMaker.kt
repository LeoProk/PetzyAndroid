package tk.leopro.petzyandroid.utilities

import android.app.Fragment
import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import tk.leopro.petzyandroid.AppController
import tk.leopro.petzyandroid.fragments.CatsAdopting
import tk.leopro.petzyandroid.fragments.DogsAdopting
import tk.leopro.petzyandroid.fragments.Found
import tk.leopro.petzyandroid.fragments.Lost
import tk.leopro.petzyandroid.fragments.OthersAdopting
import tk.leopro.petzyandroid.fragments.ParksClosest
import tk.leopro.petzyandroid.fragments.ParksMap
import tk.leopro.petzyandroid.fragments.VetsClosest
import tk.leopro.petzyandroid.fragments.VetsMap
import tk.leopro.petzyandroid.interfaces.FactoryInterface

/**
 * This Class create add tabs and on tab click.
 */
internal class TabMaker(private val mContext: Context, private val mTabLayout: TabLayout, private val mTabNames: Array<String>, private val mTags: Array<String>) : FactoryInterface {
    private var mTag: String? = null
    private var mFragment: Fragment? = null

    @Override
    fun doTask(): Object? {
        val activity = mContext as AppCompatActivity
        mTabLayout.removeAllTabs()
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL)
        for (i in mTabNames.indices) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTabNames[i]))
        }
        mTabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener() {
            @Override
            fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.getPosition()) {
                    0 -> {
                        mTag = mTags[0]
                        mFragment = getFragmentByTag(mTag)
                    }
                    1 -> {
                        mTag = mTags[1]
                        mFragment = getFragmentByTag(mTag)
                    }
                    2 -> {
                        mTag = mTags[2]
                        mFragment = getFragmentByTag(mTag)
                    }
                    3 -> {
                        mTag = mTags[3]
                        mFragment = getFragmentByTag(mTag)
                    }
                    else -> {
                    }
                }
                if (activity.getFragmentManager().findFragmentByTag(mTag) == null) {
                    //gets the application class
                    val appController = mContext.getApplicationContext() as AppController
                    appController.fragmentTag = mTag
                    UtilitiesFactory.addFragment(mContext, mFragment, mTag, true).doTask()
                } else {
                    UtilitiesFactory.switchFragments(mContext, mTag).doTask()
                }
            }

            @Override
            fun onTabUnselected(tab: TabLayout.Tab) {

            }

            @Override
            fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


        return null
    }

    fun getFragmentByTag(tag: String?): Fragment? {

        when (tag) {
            "dog" -> return DogsAdopting()
            "cat" -> return CatsAdopting()
            "other" -> return OthersAdopting()
            "lost" -> return Lost()
            "found" -> return Found()
            "parkNear" -> return ParksClosest()
            "parkMap" -> return ParksMap()
            "vetNear" -> return VetsClosest()
            "vetMap" -> return VetsMap()
        }
        return null
    }
}
