package tk.leopro.petzyandroid.Utilities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import tk.leopro.petzyandroid.AppController;

 /**
 * Created by Leo on 7/15/2015.
 */
final class PagerAdapter extends FragmentPagerAdapter {

    private String mTabTitles[];
    private Context mContext;
    private String[] mTags;
    private Fragment[] mFragmentForTabs;

    public PagerAdapter(FragmentManager fm, Context context ,String[] tabTitles,Fragment[] fragmentsforTabs,String[] tags) {
        super(fm);
        mTabTitles = tabTitles;
        mTags = tags;
        mFragmentForTabs = fragmentsforTabs;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        final AppCompatActivity activity = ( AppCompatActivity) mContext;
        if(activity.getFragmentManager().findFragmentByTag(mTags[position+1])==null){
            AppController.mFragmentTag = mTags[position+1];
            UtilitiesFactory.addFragment(mContext,mFragmentForTabs[position+1],mTags[position+1],true).doTask();
        }else {
            UtilitiesFactory.switchFragments(mContext,mTags[position+1]).doTask();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return mTabTitles[position];
    }
}
