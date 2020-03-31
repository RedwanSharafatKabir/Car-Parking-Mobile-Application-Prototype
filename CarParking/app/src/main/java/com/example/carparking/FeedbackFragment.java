package com.example.carparking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FeedbackFragment extends Fragment implements View.OnClickListener{

    Button send, clear;
    EditText name, email, feedback;
    String emailid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        name = v.findViewById(R.id.nameID);
        email = v.findViewById(R.id.emailID);
        feedback = v.findViewById(R.id.feedbackID);

        send = v.findViewById(R.id.sendID);
        send.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        try {
            String user = name.getText().toString();
            String message = feedback.getText().toString();
            String mail = email.getText().toString();

            if (v.getId() == R.id.sendID) {
                if(user.isEmpty()) {
                    email.setError("Fill this field");
                    email.setText("");
                }

                if(mail.isEmpty()) {
                    name.setError("Fill this field");
                    name.setText("");
                }

                if(message.isEmpty()) {
                    feedback.setError("Fill this field");
                    feedback.setText("");
                }

                else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    //            intent.setData(Uri.parse("mail to: "));
                    intent.setType("text/plain");

                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mohammadmunna478@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "First feedback from user");
                    intent.putExtra(Intent.EXTRA_TEXT, "Name: " + user + "\n Email address: " + mail + "\n Review: " + message);

                    startActivity(Intent.createChooser(intent, "Give feedback through: "));

                    name.setText(null);
                    feedback.setText(null);
                    email.setText(null);
                }
            }
        } catch(Exception e){
            Toast.makeText(getActivity(), "Please enter required fields", Toast.LENGTH_LONG).show();
        }
    }
}
