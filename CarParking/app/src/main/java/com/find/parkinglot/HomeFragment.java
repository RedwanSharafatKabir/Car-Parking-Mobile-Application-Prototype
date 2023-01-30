package com.find.parkinglot;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment implements View.OnClickListener {

    TextView profile, about, settings, feedback, btn1, logout;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        btn1 = (TextView) v.findViewById(R.id.findLotID);
        profile = (TextView) v.findViewById(R.id.profileID);
        about = (TextView) v.findViewById(R.id.aboutID);
        settings = (TextView) v.findViewById(R.id.settingsID);
        feedback = (TextView) v.findViewById(R.id.feedbackID);
        logout = (TextView) v.findViewById(R.id.logoutID);

        btn1.setOnClickListener(this);
        profile.setOnClickListener(this);
        about.setOnClickListener(this);
        settings.setOnClickListener(this);
        feedback.setOnClickListener(this);
        logout.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        Fragment fragment;
        if(v.getId()==R.id.findLotID){
            fragment = new FindLotFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        else if(v.getId()==R.id.profileID){
            fragment = new ProfileFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        else if(v.getId()==R.id.settingsID){
            fragment = new SettingsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        else if(v.getId()==R.id.feedbackID){
            fragment = new FeedbackFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        else if(v.getId()==R.id.aboutID){
            fragment = new AboutFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        else if(v.getId()==R.id.logoutID){
            shutDownFunction();
        }
//        else if(v.getId()==R.id.welcomeHomeID){
//            YoYo.with(Techniques.Tada)
//                    .duration(700)
//                    .repeat(1)
//                    .playOn(profile);
//            YoYo.with(Techniques.Tada)
//                    .duration(700)
//                    .repeat(1)
//                    .playOn(btn1);
//            YoYo.with(Techniques.Tada)
//                    .duration(700)
//                    .repeat(1)
//                    .playOn(settings);
//            YoYo.with(Techniques.Tada)
//                    .duration(700)
//                    .repeat(1)
//                    .playOn(about);
//            YoYo.with(Techniques.Tada)
//                    .duration(700)
//                    .repeat(1)
//                    .playOn(feedback);
//            YoYo.with(Techniques.Tada)
//                    .duration(700)
//                    .repeat(1)
//                    .playOn(logout);
//        }
    }

    public void shutDownFunction() {
        AlertDialog.Builder alertDialogBuilder;

        alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Are you sure to log out ?");
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();

                Intent it = new Intent(getActivity(), LoginScreen.class);
                startActivity(it);
            }
        });
        alertDialogBuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
