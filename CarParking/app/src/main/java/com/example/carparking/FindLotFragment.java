package com.example.carparking;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class FindLotFragment extends Fragment implements OnMapReadyCallback {

    String username;
    int j = 0;
    private GoogleMap mGoogleMap;
    ArrayList<LatLng> placelist = new ArrayList<LatLng>();
    LatLng mugdaHospital = new LatLng(23.732418, 90.430201);
    LatLng mugdaBTCL = new LatLng(23.730633, 90.428779);
    LatLng dhanmondi = new LatLng(23.755174, 90.376366);

    ArrayList<String> title = new ArrayList<String>();
    String mugdaparaHospital = "Mugda Hospital Parking Lot";
    String mugdaparaBTCLoffice = "Mugdapara BTCL Office";
    String dhanmondi_shukrabaad = "DIU Parking Lot";

    DatabaseReference databaseReference;
    String Username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_find_lot, container, false);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragmentID);
        mapFragment.getMapAsync(FindLotFragment.this);

        placelist.add(mugdaHospital);
        placelist.add(mugdaBTCL);
        placelist.add(dhanmondi);

        title.add(mugdaparaHospital);
        title.add(mugdaparaBTCLoffice);
        title.add(dhanmondi_shukrabaad);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Reserved List");

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        float zoomLevel = 19.5f;

        for(int i=0; i<placelist.size(); i++){
            if(j==i){
                mGoogleMap.addMarker(new MarkerOptions().position(placelist.get(i)).title(String.valueOf(title.get(j)))
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.lot_marker)));
            }j++;
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(placelist.get(i)));
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placelist.get(i), zoomLevel));
            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String markertitle = marker.getTitle();
                    Toast.makeText(getActivity(), markertitle, Toast.LENGTH_SHORT).show();
                    BookingAlertDialogFunc(markertitle);

                    return false;
                }
            });
        }
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
                StoreReservedListData(markertitle);
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

    public void StoreReservedListData(String markertitle){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            if(user.getDisplayName()!=null) {
                username = user.getDisplayName();
            }
        }

        String Key_User_Info = username;
        StoreReservedData storeReservedData = new StoreReservedData(markertitle);
        databaseReference.child(Key_User_Info).setValue(storeReservedData);
    }
}
