package com.example.carparking;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReserveListFragment extends Fragment implements AdapterView.OnItemClickListener{

    TextView textView;
    ImageView close, bkash, dbbl;
    Dialog dialog;
    ListView listview;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    CustomAdapter customAdapter;
    List<StoreReservedData> userLotList;
    ArrayAdapter<String> adpt;
    String value;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_reserve_list, container, false);

        listview = v.findViewById(R.id.reserveListViewID);
        listview.setOnItemClickListener(this);
        dialog = new Dialog(getActivity());

        databaseReference = FirebaseDatabase.getInstance().getReference("Reserved List");
        userLotList = new ArrayList<StoreReservedData>();
        customAdapter = new CustomAdapter(getActivity(), userLotList);
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        value = user.getUid();

        return v;
    }

    @Override
    public void onStart() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userLotList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    StoreReservedData storeReservedData = dataSnapshot1.getValue(StoreReservedData.class);
                    userLotList.add(storeReservedData);
                }
                listview.setAdapter(customAdapter);
//                String markertitle = dataSnapshot.child(value).child("MarketTitle").getValue(String.class);
//                userLotList.add(markertitle);
//                adpt = new ArrayAdapter<String>(getActivity(), R.layout.array_adapter, userLotList);
//                listview.setAdapter(adpt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        FirebaseUser lotlist = mAuth.getCurrentUser();
//        if(lotlist!=null){
//            if(lotlist.getDisplayName()!=null) {
//                value = lotlist.getDisplayName();
//            }
//        }
//        adpt = new ArrayAdapter<String>(getActivity(), R.layout.array_adapter, R.id.parking_lot_ID1, parking_lot);
        showLotDetails(value);
    }

    public void showLotDetails(String value){
        dialog.setContentView(R.layout.reserved_lot_name_details);

        close = (ImageView) dialog.findViewById(R.id.closeDialogID);
        textView = (TextView) dialog.findViewById(R.id.outputLotNameID);
        bkash = (ImageView) dialog.findViewById(R.id.bkashButtonID);
        dbbl = (ImageView) dialog.findViewById(R.id.dbblButtonID);

        textView.setText(value);

        bkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(getActivity(), "Thank you for paying with bKash", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                dialog.dismiss();
            }
        });

        dbbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(getActivity(), "Thank you for paying with DBBL mobile banking", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(false);
    }
}
