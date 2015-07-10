package tk.leopro.petzyandroid.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;

import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.UserInterface.UIFactory;

/**
 * Show closest veterinars on the map
 */
public class VetsMap extends Fragment {

    private MapView mMapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.vets_fragment, container, false);
        // Gets the MapView from the XML layout and creates it
        mMapView = (MapView) rootView.findViewById(R.id.vets_map);
        mMapView.onCreate(savedInstanceState);
        UIFactory.getMap(getActivity(), mMapView).doTask();
        return rootView;
    }


}
