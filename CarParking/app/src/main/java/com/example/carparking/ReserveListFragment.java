package com.example.carparking;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReserveListFragment extends Fragment implements AdapterView.OnItemClickListener{

    TextView textView;
    ImageView close, bkash, dbbl;
    Dialog dialog;
    ListView listview;
    private String parking_lot[];
    ArrayAdapter<String> adpt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_reserve_list, container, false);

        listview = v.findViewById(R.id.reserveListViewID);
        parking_lot = getResources().getStringArray(R.array.parking_lot_array);
        adpt = new ArrayAdapter<String>(getActivity(), R.layout.array_adapter, R.id.parking_lot_ID1, parking_lot);
        listview.setAdapter(adpt);
        listview.setOnItemClickListener(this);
        dialog = new Dialog(getActivity());

//        Bundle bundle = getArguments();
//        String first_key = bundle.getString("Key1");
//        sample.setText(first_key);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String value = adpt.getItem(position);
        showLotDetails(value);
    }

    public void showLotDetails(String value){
        dialog.setContentView(R.layout.reserved_lot_name_details);

        close = (ImageView) dialog.findViewById(R.id.closeDialogID);
        textView = (TextView) dialog.findViewById(R.id.outputLotNameID);
        bkash = (ImageView) dialog.findViewById(R.id.bkashButtonID);
        dbbl = (ImageView) dialog.findViewById(R.id.dbblButtonID);

        textView.setText(value);

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
