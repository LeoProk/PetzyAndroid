package tk.leopro.petzyandroid.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.leopro.petzyandroid.R;

/**
 * Created by Leo on 7/23/2015.
 */
public class Found extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.found_fragment, container, false);
        return rootView;
    }
}
