package com.example.carparking;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FindLotFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_find_lot, container, false);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragmentID);
        mapFragment.getMapAsync(FindLotFragment.this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Add a marker in Dhaka and move the camera
        LatLng mugda = new LatLng(23.732228, 90.430006);
        mGoogleMap.addMarker(new MarkerOptions().position(mugda).title("Mugda Hospital Parking Lot"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mugda));
        // zoom camera of mugda map
        float zoomLevel1 = 20f;    //This goes up to 21
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mugda, zoomLevel1));

        // Add a marker in dhanmondi and move the camera
        LatLng dhanmondi = new LatLng(23.755174, 90.376366);
        mGoogleMap.addMarker(new MarkerOptions().position(dhanmondi).title("DIU Parking Lot"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(dhanmondi));
        // zoom camera of dhanmondi map
        float zoomLevel2 = 20f;    //This goes up to 21
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhanmondi, zoomLevel2));
    }
}
