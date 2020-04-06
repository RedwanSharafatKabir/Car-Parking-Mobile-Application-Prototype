package com.example.carparking;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FindLotFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener{

    ImageView imageView;
    EditText inputSearch;
    float zoomLevel;
    String username;
    int j = 0, i = 0;
    private GoogleMap mGoogleMap;
    private static final String TAG = "FindLotFragment";
    private boolean locationPermissionGranted = true;
    FusedLocationProviderClient mfusedLocationProviderClient;

    LatLng DevicelatLng;
    ArrayList<LatLng> placelist = new ArrayList<LatLng>();
    LatLng dhanmondi = new LatLng(23.755174, 90.376366);
    LatLng mugdaHospital = new LatLng(23.732418, 90.430201);
    LatLng mugdaBTCL = new LatLng(23.730633, 90.428779);

    ArrayList<String> title = new ArrayList<String>();
    String dhanmondi_shukrabaad = "DIU Parking Lot";
    String mugdaparaHospital = "Mugda Hospital Parking Lot";
    String mugdaparaBTCLoffice = "Mugdapara BTCL Office";

    DatabaseReference databaseReference;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_find_lot, container, false);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragmentID);
        mapFragment.getMapAsync(FindLotFragment.this);

        placelist.add(dhanmondi);
        placelist.add(mugdaHospital);
        placelist.add(mugdaBTCL);

        title.add(dhanmondi_shukrabaad);
        title.add(mugdaparaHospital);
        title.add(mugdaparaBTCLoffice);

        imageView = v.findViewById(R.id.getDeviceID);
        imageView.setOnClickListener(this);
        inputSearch = v.findViewById(R.id.searchMapID);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Reserved List");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            if(user.getDisplayName()!=null) {
                username = user.getDisplayName();
            }
        }

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        zoomLevel = 15f;

        MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map_style);
        mGoogleMap.setMapStyle(mapStyleOptions);

        for(i=0; i<placelist.size(); i++){
            if(j==i){
                mGoogleMap.addMarker(new MarkerOptions().position(placelist.get(i)).title(String.valueOf(title.get(j)))
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.lot_marker)));
            }j++;
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(placelist.get(i)));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placelist.get(i), zoomLevel));
            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String markertitle = marker.getTitle();
                    if(markertitle.equals(username)){
                        Toast.makeText(getActivity(), "You cannot park your car here", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), markertitle, Toast.LENGTH_SHORT).show();
                        BookingAlertDialogFunc(markertitle);
                    }
                    return false;
                }
            });
        }

        Toast.makeText(getActivity(), "Map is ready", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onMapReady: Map is ready");
        if(locationPermissionGranted){
            getDeviceLocation();
            mGoogleMap.setMyLocationEnabled(true);
            init();
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    private void init(){
        Log.d(TAG, "init: initialization");

        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_SEARCH || actionId==EditorInfo.IME_ACTION_DONE
                    || event.getAction()==KeyEvent.ACTION_DOWN || event.getAction()==KeyEvent.KEYCODE_ENTER){
                    geoLocate();
                }
                return false;
            }
        });
    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geoLocating");
        String searchString = inputSearch.getText().toString();
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch(IOException e){
            Log.d(TAG, "geoLocate: ioexception" + e.getMessage());
        }
        if(list.size()>0){
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found a location" + address.toString());

            LatLng SearchlatLng = new LatLng(address.getLatitude(), address.getLongitude());
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(SearchlatLng));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SearchlatLng, zoomLevel));
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: get current device location");
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            if(locationPermissionGranted){
                Task location = mfusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: location found");
                            Location currentLocation = (Location) task.getResult();

                            DevicelatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            Log.d(TAG, "moveCamera: move camera to: lat: " + DevicelatLng.latitude + ", lng: " + DevicelatLng.longitude);
                            mGoogleMap.addMarker(new MarkerOptions().position(DevicelatLng).title(username)
                                    .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.self_location)));
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(DevicelatLng));
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DevicelatLng, zoomLevel));
                        } else {
                            Log.d(TAG, "onComplete: current location null!");
                            Toast.makeText(getActivity(), "Cannot get device location", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){Log.d(TAG, "getDeviceLocation: SecurityException" + e.getMessage());}
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int VectorID) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, VectorID);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void BookingAlertDialogFunc(final String markertitle) {
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Do you want to park your car at " + markertitle + " ?");
        alertDialogBuilder.setIcon(R.drawable.park_marker);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String saveCurrentDate, saveCurrentTime;
                Calendar c = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                saveCurrentDate = currentDate.format(c.getTime());
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(c.getTime());

                StoreReservedListData(markertitle, saveCurrentDate, saveCurrentTime);
                Toast.makeText(getActivity(), "Added to your reserve list", Toast.LENGTH_LONG).show();
            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void StoreReservedListData(String markertitle, String saveCurrentDate, String saveCurrentTime){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            if(user.getDisplayName()!=null) {
                username = user.getDisplayName();
            }
        }

        String Key_User_Info = username;
        StoreReservedData storeReservedData;
        storeReservedData = new StoreReservedData(markertitle, saveCurrentDate, saveCurrentTime);
        databaseReference.child(Key_User_Info).setValue(storeReservedData);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.getDeviceID){
            getDeviceLocation();
        }
    }
}
