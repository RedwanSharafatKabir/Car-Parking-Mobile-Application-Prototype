package com.example.carparking;

import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReserveListFragment extends Fragment {

    TextView textView, reservationText1, reservationText2;
    ImageView bkash, dbbl;
    DatabaseReference databaseReference;
    String username, markertitle,  saveCurrentDate, saveCurrentTime;
    int totalHour, totalMinute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_reserve_list, container, false);

        bkash = (ImageView) v.findViewById(R.id.bkashButtonID);
        dbbl = (ImageView) v.findViewById(R.id.dbblButtonID);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Reserved List");

        textView = v.findViewById(R.id.parking_lot_ID1);
        reservationText1 = v.findViewById(R.id.reservationTimeID1);
        reservationText2 = v.findViewById(R.id.reservationTimeID2);

        bkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null) {
                    if (user.getDisplayName() != null) {
                        username = user.getDisplayName();

                        markertitle="";
                        saveCurrentDate="";
                        saveCurrentTime="";

                        String Key_User_Info = username;
                        StoreReservedData storeReservedData;
                        storeReservedData = new StoreReservedData(markertitle, saveCurrentDate, saveCurrentTime);
                        databaseReference.child(Key_User_Info).setValue(storeReservedData);
//                        DatabaseReference deleteRef1 = databaseReference.child(username).child("saveCurrentDate");
//                        deleteRef5.removeValue();
                    }
                }
                Toast t = Toast.makeText(getActivity(), "Thank you for paying with bKash", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        });

        dbbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null) {
                    if (user.getDisplayName() != null) {
                        username = user.getDisplayName();

                        markertitle="";
                        saveCurrentDate="";
                        saveCurrentTime="";

                        String Key_User_Info = username;
                        StoreReservedData storeReservedData;
                        storeReservedData = new StoreReservedData(markertitle, saveCurrentDate, saveCurrentTime);
                        databaseReference.child(Key_User_Info).setValue(storeReservedData);
//                        DatabaseReference deleteRef1 = databaseReference.child(username).child("saveCurrentDate");
//                        deleteRef5.removeValue();
                    }
                }
                Toast t = Toast.makeText(getActivity(), "Thank you for paying with DBBL mobile banking", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            if(user.getDisplayName()!=null) {
                username = user.getDisplayName();

                DatabaseReference userRef1 = databaseReference.child(username).child("markertitle");
                userRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        textView.setText(dataSnapshot.getValue(String.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });

                DatabaseReference userRef2 = databaseReference.child(username).child("saveCurrentDate");
                userRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        reservationText1.setText(dataSnapshot.getValue(String.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });

                DatabaseReference userRef3 = databaseReference.child(username).child("saveCurrentTime");
                userRef3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        reservationText2.setText(dataSnapshot.getValue(String.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        }
        super.onStart();
    }
}
