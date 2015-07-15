package tk.leopro.petzyandroid.Utilities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Leo on 7/15/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private String mTabTitles[];
    private Context mContext;
    private Fragment[] mFragmentForTabs;

    public PagerAdapter(FragmentManager fm, Context context ,String[] tabTitles) {
        super(fm);
        mTabTitles = tabTitles;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
