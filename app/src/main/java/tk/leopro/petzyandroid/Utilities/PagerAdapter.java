package tk.leopro.petzyandroid.Utilities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.Fragments.LostFound;
import tk.leopro.petzyandroid.Fragments.VetsMap;

/**
 * Created by Leo on 7/15/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    /*private Context mContext;
    private String[] mTags;
    private Fragment[] mFragmentForTabs;*/

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        Log.e("WORKS!", "3333333333");
      /*  mTags = tags;
        mFragmentForTabs = fragmentsforTabs;*/
       // mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Log.e("TAB!", "YAAAAAAAAAAAAAA");
                return new LostFound();
            case 1:
                Log.e("TAB2", "YAAAAAAAAAAAAAA");
                return new VetsMap();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }


}
