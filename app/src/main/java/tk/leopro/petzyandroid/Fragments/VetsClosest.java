package tk.leopro.petzyandroid.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tk.leopro.petzyandroid.MainActivity;
import tk.leopro.petzyandroid.R;

/**
 * Created by Leo on 7/24/2015.
 */
public class VetsClosest extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.vets_closest_fragment, container, false);
        ((MainActivity)getActivity()).changeTabs();
        return rootView;
    }
}
