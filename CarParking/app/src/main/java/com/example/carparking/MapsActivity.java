package com.example.carparking;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    FusedLocationProviderClient client;
    Location currentLocation;
    private static final int REQUEST_VODE = 101;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);

        client = LocationServices.getFusedLocationProviderClient(this);
        locationMethod();

    }

    public void locationMethod(){
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation = location;
//                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragmentID);
//                    mapFragment.getMapAsync( MapsActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        LatLng lantlang = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(lantlang).title("It's me");
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(lantlang));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lantlang, 5));
        mGoogleMap.addMarker(markerOptions);

//        LatLng dhaka = new LatLng(23.777176, 90.399452);
//        mGoogleMap.addMarker(new MarkerOptions().position(dhaka).title("Parking Lot"));
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_VODE:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    locationMethod();
                }
                break;
        }
    }
}
