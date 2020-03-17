package com.example.carparking;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.core.Context;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<StoreData> {

    Activity context;
    List<StoreData> userInfoList;

    public CustomAdapter(Activity context, List<StoreData> userInfoList) {
        super(context, R.layout.sample_layout, userInfoList);
        this.context = context;
        this.userInfoList = userInfoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sample_layout, null, true);

        StoreData storeData = userInfoList.get(position);

        TextView Username = view.findViewById(R.id.usernameBelowProfilePicID);
        TextView Email = view.findViewById(R.id.emailBelowProfilePicID);

//        Username.setText(" Name: " + storeData.getUsername());
//        Email.setText(" Email: " + storeData.getEmail());

        return view;
    }
}
