package com.example.carparking;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView nameTextview, emailTextview;
    Button youareawesome;
    ListView listView;
    List<StoreData> userInfoList;
    CustomAdapter customAdapter;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String userInfo = "User Information Table", Email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Intent intent = getActivity().getIntent();
        Email = intent.getStringExtra("Email");

        youareawesome = v.findViewById(R.id.youAwesomeButtonID);
        youareawesome.setOnClickListener(this);

        nameTextview = v.findViewById(R.id.usernameBelowProfilePicID);
        emailTextview = v.findViewById(R.id.emailBelowProfilePicID);

//        listView = v.findViewById(R.id.userProfileListID);
//        userInfoList = new ArrayList<>();
//        customAdapter = new CustomAdapter(getActivity(), userInfoList);

        databaseReference = FirebaseDatabase.getInstance().getReference(userInfo);

        return v;
    }

    @Override
    public void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                userInfoList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("email").getValue().equals(Email)){
                        nameTextview.setText(dataSnapshot1.child("username").getValue(String.class));
                        emailTextview.setText(Email);
                    }
//                    StoreData storeData = dataSnapshot1.getValue(StoreData.class);
//                    userInfoList.add(storeData);
                }
//                listView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.youAwesomeButtonID){
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(1)
                    .playOn(nameTextview);
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(1)
                    .playOn(emailTextview);
        }
    }
}
