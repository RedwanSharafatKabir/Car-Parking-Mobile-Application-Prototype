package com.example.carparking;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragmentID);
        mapFragment.getMapAsync( this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        // Add a marker in Dhaka and move the camera
        LatLng dhaka = new LatLng(23.777176, 90.399452);
        mGoogleMap.addMarker(new MarkerOptions().position(dhaka).title("Parking Lot"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));

        // zoom camera of google map
        float zoomLevel = 19.5f;    //This goes up to 21
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, zoomLevel));
    }
}
