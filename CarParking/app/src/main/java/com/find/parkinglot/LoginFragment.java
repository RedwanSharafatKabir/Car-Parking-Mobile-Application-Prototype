package com.find.parkinglot;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment implements View.OnClickListener{

    Button signupPage, forgetPassButton;
    ImageButton signin;
    EditText usernameEmail, password;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();

        usernameEmail = (EditText) v.findViewById(R.id.loginEmailID);
        password = (EditText) v.findViewById(R.id.loginpassID);

        signupPage = (Button) v.findViewById(R.id.signupPageID);
        signupPage.setOnClickListener(this);

        signin = (ImageButton) v.findViewById(R.id.SigninID);
        signin.setOnClickListener(this);

        forgetPassButton = (Button) v.findViewById(R.id.forgetPassID);
        forgetPassButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        final String emailobj = usernameEmail.getText().toString();
        String passobj = password.getText().toString();

        if(v.getId()==R.id.signupPageID){
            Fragment fragment = new SignupFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.loginSignupFragmentID, fragment).commit();
        }

        if(v.getId()==R.id.SigninID){
            if (emailobj.isEmpty()) {
                usernameEmail.setError("Fill this field");
                usernameEmail.setText("");
            }
            if(passobj.isEmpty()){
                password.setError("Fill this field");
                password.setText("");
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(emailobj).matches()) {
                Toast t = Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                usernameEmail.requestFocus();
                return;
            }

            if(!emailobj.isEmpty() && !passobj.isEmpty()) {
                mAuth.signInWithEmailAndPassword(emailobj, passobj).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast toast = Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            getActivity().finish();

                            Intent it = new Intent(getActivity(), MainActivity.class);
                            startActivity(it);

                            usernameEmail.setText("");
                            password.setText("");
                        } else {
                            Toast t = Toast.makeText(getActivity(), "Authentication failed\nError : " +
                                    task.getException().getMessage(), Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            t.show();
                        }
                    }
                });
            }
        }
    }
}
