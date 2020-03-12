package com.example.carparking;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class LoginFragment extends Fragment implements View.OnClickListener{

    Button signupPage;
    ImageButton signin;
    EditText usernameEmail, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        signin = (ImageButton) v.findViewById(R.id.SigninID);
        signin.setOnClickListener(this);
        usernameEmail = (EditText) v.findViewById(R.id.loginEmailID);
        password = (EditText) v.findViewById(R.id.loginpassID);
        signupPage = (Button) v.findViewById(R.id.signupPageID);
        signupPage.setOnClickListener(this);

        return v;
    }
    @Override
    public void onClick(View v) {
        String email = usernameEmail.getText().toString();
        String pass = password.getText().toString();

        if(v.getId()==R.id.SigninID){
//            if (email.isEmpty()) {
//                usernameEmail.setError("Fill up all required fields");
//                usernameEmail.setText("");
//            }
//            if(pass.isEmpty()){
//                password.setError("Fill up all required fields");
//                password.setText("");
//            }
//            else {
                usernameEmail.setText("");
                password.setText("");

                ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("Loading...").build();
                dialog.show();

                Intent it = new Intent(getActivity(), MainActivity.class);
                startActivity(it);
//            }
        }
        if(v.getId()==R.id.signupPageID){
            Fragment fragment = new SignupFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.loginSignupFragmentID, fragment).commit();
        }
    }
}
