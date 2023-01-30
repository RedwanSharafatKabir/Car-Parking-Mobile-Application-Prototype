package com.find.parkinglot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final int CHOOSE_IMAGE = 1;
    CircleImageView circleImageView;
    TextView nameTextview, emailTextview;
    EditText phoneTextview;
    Button youareawesome, save;
    FirebaseAuth mAuth;
    Uri uriProfileImage;
    String profileImageUrl, phoneNumber, Username;
    StorageReference profileImageRef;
    DatabaseReference databaseReference, reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        youareawesome = v.findViewById(R.id.youAwesomeButtonID);
        youareawesome.setOnClickListener(this);
        save = v.findViewById(R.id.saveButtonID);
        save.setOnClickListener(this);

        nameTextview = v.findViewById(R.id.usernameBelowProfilePicID);
        emailTextview = v.findViewById(R.id.emailBelowProfilePicID);
        phoneTextview = v.findViewById(R.id.phoneBelowProfilePicID);

        circleImageView = v.findViewById(R.id.profilePicID);
        circleImageView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");
        reference = FirebaseDatabase.getInstance().getReference("Phone Number");

        return v;
    }

    @Override
    public void onStart() {
        FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null){
            if(user.getPhotoUrl()!=null){
                Glide.with(getActivity()).load(user.getPhotoUrl().toString()).into(circleImageView);
            }
            if(user.getDisplayName()!=null) {
                nameTextview.setText(user.getDisplayName());
                Username = user.getDisplayName();
            }
            if(user.getEmail()!=null){
                emailTextview.setText(" " + user.getEmail());
            }
            DatabaseReference userRef1 = reference.child(Username).child("phoneNumber");
            userRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    phoneTextview.setText(" " + dataSnapshot.getValue(String.class));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.profilePicID){
            showImageChooser();
        }

        if(v.getId()==R.id.youAwesomeButtonID){
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(1)
                    .playOn(circleImageView);
        }

        if(v.getId()==R.id.saveButtonID){
            phoneNumber = phoneTextview.getText().toString();

            String Key_User_Info = Username;
            StorePhoneNumber storePhoneNumber;
            storePhoneNumber = new StorePhoneNumber(phoneNumber);
            reference.child(Key_User_Info).setValue(storePhoneNumber);
        }
    }

    public void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CHOOSE_IMAGE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uriProfileImage = data.getData();

            Picasso.get().load(uriProfileImage).into(circleImageView);

            uploadImageToFirebase();
        }
    }

    public void uploadImageToFirebase() {
        profileImageRef = FirebaseStorage.getInstance()
                .getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if(uriProfileImage!=null){
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    profileImageUrl = uri.toString();
                                    Toast.makeText(getActivity(), "Image Upload Successful", Toast.LENGTH_SHORT).show();
                                    saveUserInfo();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {}
                    });
        }
    }

    public void saveUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null && profileImageUrl!=null){
            UserProfileChangeRequest profile2;
            profile2= new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(profileImageUrl)).build();

            user.updateProfile(profile2).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {}
            });
        }
    }
}
