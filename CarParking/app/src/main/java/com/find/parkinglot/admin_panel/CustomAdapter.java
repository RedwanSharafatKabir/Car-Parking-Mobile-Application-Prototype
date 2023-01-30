package com.find.parkinglot.admin_panel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.find.parkinglot.R;
import com.find.parkinglot.StoreData;
import java.util.*;

public class CustomAdapter extends ArrayAdapter<StoreData> {

    private Activity context;
    List<StoreData> storeDataList;

    public CustomAdapter(Activity context, List<StoreData> storeDataList) {
        super(context, R.layout.sample_layout, storeDataList);
        this.context = context;
        this.storeDataList = storeDataList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sample_layout, null, true);

        StoreData storeData = storeDataList.get(position);

        TextView textView1 = view.findViewById(R.id.showDetailID1);
        TextView textView2 = view.findViewById(R.id.showDetailID2);
        textView1.setText(storeData.getUsername());
        textView2.setText(storeData.getEmail());

        return view;
    }
}
