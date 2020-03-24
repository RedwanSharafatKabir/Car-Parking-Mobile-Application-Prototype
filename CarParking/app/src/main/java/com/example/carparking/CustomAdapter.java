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

public class CustomAdapter extends ArrayAdapter<StoreReservedData> {

    Activity context;
    List<StoreReservedData> userLotList;

    public CustomAdapter(Activity context, List<StoreReservedData> userLotList) {
        super(context, R.layout.array_adapter, userLotList);
        this.context = context;
        this.userLotList = userLotList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.array_adapter, null, true);

        StoreReservedData storeReservedData = userLotList.get(position);

        TextView textView = view.findViewById(R.id.parking_lot_ID1);

        textView.setText(storeReservedData.getMarkertitle());

        return view;
    }
}
