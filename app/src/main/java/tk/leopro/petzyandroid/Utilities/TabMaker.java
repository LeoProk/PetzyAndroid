package tk.leopro.petzyandroid.Utilities;

import android.app.Fragment;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.Interfaces.FactoryInterface;
import tk.leopro.petzyandroid.R;

/**
 * This Class create add tabs and on tab click.
 */
final class TabMaker implements FactoryInterface {

    private Context mContext;
    private TabLayout mTabLayout;
    private String[] mTabNames;
    private String[] mTags;
    private Fragment[] mTabFragments;
    private String mTag;
    private Fragment mFragment;

    public TabMaker (Context context,TabLayout tabLayout,String[] tabNames ,String[] tags, Fragment[] tabFragments){
        mContext = context;
        mTabLayout = tabLayout;
        mTabNames = tabNames;
        mTags = tags;
        mTabFragments = tabFragments;

    }

    @Override
    public Object doTask() {
        final AppCompatActivity activity = ( AppCompatActivity) mContext;
        mTabLayout.removeAllTabs();
        for (int i = 0; i < mTabNames.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTabNames[i]));
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mFragment = mTabFragments[0];
                        mTag = mTags[0];
                        break;
                    case 1:
                        mFragment = mTabFragments[1];
                        mTag = mTags[1];
                        break;
                    case 2:
                        mFragment = mTabFragments[2];
                        mTag = mTags[2];
                        break;
                    case 3:
                        mFragment = mTabFragments[3];
                        mTag = mTags[3];
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(activity.getFragmentManager().findFragmentByTag(mTag)==null){
            AppController.mFragmentTag = mTag;
            UtilitiesFactory.addFragment(mContext,mFragment,mTag,true).doTask();
        }else {
            UtilitiesFactory.switchFragments(mContext,mTag).doTask();
        }
        return null;
    }
}
