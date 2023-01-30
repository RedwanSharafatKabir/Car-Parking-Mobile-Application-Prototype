package com.find.parkinglot.admin_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.find.parkinglot.R;
import com.find.parkinglot.StoreData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminHomePage extends AppCompatActivity {

    ListView listView;
    DatabaseReference databaseReference;
    String Username, Email;
    List<StoreData> storeDataList;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        listView = findViewById(R.id.ListViewID);

        databaseReference = FirebaseDatabase.getInstance().getReference("User Information");
        storeDataList = new ArrayList<>();
        customAdapter = new CustomAdapter(AdminHomePage.this, storeDataList);
    }

    @Override
    protected void onStart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            if(user.getDisplayName()!=null) {
                Username = user.getDisplayName();
            }

            if(user.getEmail()!=null){
                Email = user.getEmail();
            }

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    storeDataList.clear();
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        StoreData storeData = dataSnapshot1.getValue(StoreData.class);
                        storeDataList.add(storeData);
                    }
                    listView.setAdapter(customAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }

        super.onStart();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder;

        alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Are you sure to leave ?\n you will be logged out");
        alertDialogBuilder.setIcon(R.drawable.exit);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent it = new Intent(AdminHomePage.this, AdminLoginScreen.class);
                startActivity(it);
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
}
