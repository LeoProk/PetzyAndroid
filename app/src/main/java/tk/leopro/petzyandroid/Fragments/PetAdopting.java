package tk.leopro.petzyandroid.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.leopro.petzyandroid.R;

/**
 * This fragment show list of pet available for adopting sorted by time . also it have
 * option to add pets that you want other to adopt
 */
public class PetAdopting extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.adopting_fragment, container, false);
        return rootView;
    }


}
