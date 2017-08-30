package tk.leopro.petzyandroid.utilities;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import tk.leopro.petzyandroid.interfaces.FactoryInterface;

/**
 * This class contain logic of toolbar
 */
final class CustomToolbar implements FactoryInterface {

    private Context mContext;

    private Toolbar mToolbar;

    protected CustomToolbar(Context context, Toolbar toolbar) {
        this.mContext = context;
        this.mToolbar = toolbar;
    }

    //replace the default action bat with toolbar
    @Override
    public Object doTask() {
        final AppCompatActivity activity = (AppCompatActivity) mContext;
        activity.setSupportActionBar(mToolbar);
        ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayUseLogoEnabled(true);
        /*activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);*/
        return null;
    }
}
