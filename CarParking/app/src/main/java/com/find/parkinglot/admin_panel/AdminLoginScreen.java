package com.find.parkinglot.admin_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.find.parkinglot.HomeScreen;
import com.find.parkinglot.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginScreen extends AppCompatActivity implements View.OnClickListener{

    Button signin;
    EditText email, password;
    DatabaseReference databaseReference;
    String admin1Email, admin1Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_login_screen);

        signin = findViewById(R.id.adminSigninID);
        signin.setOnClickListener(this);

        email = findViewById(R.id.adminLoginEmailID);
        password = findViewById(R.id.adminLoginpassID);

        databaseReference = FirebaseDatabase.getInstance().getReference("Admin Information");

        DatabaseReference userRef1 = databaseReference.child("Admin 1").child("Email");
        userRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                admin1Email = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        DatabaseReference userRef2 = databaseReference.child("Admin 1").child("password");
        userRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                admin1Password = Long.toString((Long) dataSnapshot.getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void onClick(View v) {
        String eml = email.getText().toString();
        String pass = password.getText().toString();

        if(v.getId()==R.id.adminSigninID) {
            if (eml.isEmpty()) {
                email.setError("Fill this field");
                email.setText("");
            }

            if (pass.isEmpty()) {
                password.setError("Fill this field");
                password.setText("");
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(eml).matches()) {
                Toast t = Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                email.requestFocus();
                return;
            }

            else if(eml.equals(admin1Email) && pass.equals(admin1Password)) {
                Intent it = new Intent(getApplicationContext(), AdminHomePage.class);
                startActivity(it);
                Toast.makeText(AdminLoginScreen.this, "Login successfull", Toast.LENGTH_LONG).show();
                email.setText("");
                password.setText("");
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(getApplicationContext(), HomeScreen.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
