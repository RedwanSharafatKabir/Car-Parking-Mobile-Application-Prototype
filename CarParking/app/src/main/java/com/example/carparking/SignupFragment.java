package com.example.carparking;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class SignupFragment extends Fragment implements View.OnClickListener {

    Button signinPage;
    ImageButton signup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup, container, false);

        signinPage = (Button) v.findViewById(R.id.signInPageID);
        signinPage.setOnClickListener(this);

        signup = (ImageButton) v.findViewById(R.id.SignupID);
        signup.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.SignupID){
            ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text("Loading...").build();
            dialog.show();

            Intent it = new Intent(getActivity(), MainActivity.class);
            startActivity(it);
        }

        if(v.getId()==R.id.signInPageID){
            Fragment fragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.loginSignupFragmentID, fragment).commit();
        }
    }
}
