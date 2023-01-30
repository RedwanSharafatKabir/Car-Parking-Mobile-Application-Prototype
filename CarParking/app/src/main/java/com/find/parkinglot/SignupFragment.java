package com.find.parkinglot;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupFragment extends Fragment implements View.OnClickListener {

    DatabaseReference databaseReference;
    Button signinPage;
    ImageButton signup;
    EditText Editemail, Editpass, confirmpass, usernm;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup, container, false);

        signinPage = (Button) v.findViewById(R.id.signInPageID);
        signinPage.setOnClickListener(this);

        signup = (ImageButton) v.findViewById(R.id.SignupID);
        signup.setOnClickListener(this);

        Editemail = (EditText) v.findViewById(R.id.signupEmailID);
        usernm = (EditText) v.findViewById(R.id.signupUsernameID);
        Editpass = (EditText) v.findViewById(R.id.signupPasswordID);
        confirmpass = (EditText) v.findViewById(R.id.confirmPasswordID);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User Information");

        return v;
    }

    @Override
    public void onClick(View v) {
        final String Email = Editemail.getText().toString().trim();
        final String Username = usernm.getText().toString().trim();
        final String Password = Editpass.getText().toString().trim();
        String ConfirmPassword = confirmpass.getText().toString().trim();

        if(v.getId()==R.id.signInPageID){
            Fragment fragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.loginSignupFragmentID, fragment).commit();
        }

        if(v.getId()==R.id.SignupID){

            if(Email.isEmpty()) {
                Editemail.setError("Fill this field");
                Editemail.setText("");
            }
            if(Username.isEmpty()) {
                usernm.setError("Fill this field");
                usernm.setText("");
            }
            if(Password.isEmpty()) {
                Editpass.setError("Fill this field");
                Editpass.setText("");
            }
            if(ConfirmPassword.isEmpty()) {
                confirmpass.setError("Fill this field");
                confirmpass.setText("");
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                Toast t = Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                Editemail.requestFocus();
                return;
            }

            if (Password.length() < 8) {
                Editpass.setError("Password is too short.\nPassword must contain minimum 8 characters");
                Editpass.setText("");
                confirmpass.setText("");
            }

            if ((Password.length() >= 8) && (Password.matches(ConfirmPassword))) {

                mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            storeUserData(Email, Username);

                            Toast toast = Toast.makeText(getActivity(), "Successfully registered", Toast.LENGTH_LONG);
                            toast.show();

                            Fragment fragment = new LoginFragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.loginSignupFragmentID, fragment).commit();

                            Editemail.setText("");
                            usernm.setText("");
                            Editpass.setText("");
                            confirmpass.setText("");
                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast t = Toast.makeText(getActivity(), "User is already registered", Toast.LENGTH_LONG);
                                t.setGravity(Gravity.CENTER, 0, 0);
                                t.show();
                            }
                            else {
                                Toast t = Toast.makeText(getActivity(), "Authentication failed. Error : " +
                                        task.getException().getMessage(), Toast.LENGTH_LONG);
                                t.setGravity(Gravity.CENTER, 0, 0);
                                t.show();
                            }
                        }
                    }
                });
            }

            else {
                Toast toast = Toast.makeText(getActivity(), "Password did not match", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Editemail.requestFocus();
                Editpass.requestFocus();
            }
        }
    }

    public void storeUserData(String Email, String Username){

        String displayname = Username;
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            UserProfileChangeRequest profile;
            profile= new UserProfileChangeRequest.Builder().setDisplayName(displayname).build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {}
            });
        }

//        String Key_User_Info = databaseReference.push().getKey();
        String Key_User_Info = Username;

        StoreData storeData = new StoreData(Email, Username);
        databaseReference.child(Key_User_Info).setValue(storeData);
    }
}

//        ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
//                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
//                .themeColor(Color.WHITE)
//                .text("Loading...").build();
//        dialog.show();
