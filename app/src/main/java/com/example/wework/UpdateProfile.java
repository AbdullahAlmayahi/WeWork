package com.example.wework;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UpdateProfile extends Fragment {

    EditText edittextUsername, editTextMobile, EditTextNationality;
    RadioButton updateGender;
    RadioGroup update_profile_gender;
    String Username, Mobile, Gender, Nationality;
    FirebaseAuth authProfile;
    Button Updatebtn;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_update_profile, container, false);

        edittextUsername = v.findViewById(R.id.inputUsername);
        editTextMobile = v.findViewById(R.id.inputMobile);
        EditTextNationality = v.findViewById(R.id.inputNationality);
        update_profile_gender = v.findViewById(R.id.update_profile_gender);


        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        // show profile data
        showProfile(firebaseUser);


        // update button
        Updatebtn = v.findViewById(R.id.btnUpdateX);
        Updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

// Select Gender and Nationality


                // Check if user fill all the fields before sending data to firebase
                if (TextUtils.isEmpty(Username)) {
                    Toast.makeText(getContext(), "Please enter your Username", Toast.LENGTH_LONG).show();
                    edittextUsername.setError("Username is Required");
                    edittextUsername.requestFocus();
                } else if (TextUtils.isEmpty(Mobile)) {
                    Toast.makeText(getContext(), "Please enter your Mobile number", Toast.LENGTH_LONG).show();
                    editTextMobile.setError("Mobile number is Required");
                    editTextMobile.requestFocus();
                } else if (Mobile.length() !=8) {
                    Toast.makeText(getContext(), "Please re-enter your Mobile number", Toast.LENGTH_LONG).show();
                    editTextMobile.setError("Mobile number is Should be 8 digits");
                    editTextMobile.requestFocus();
                } else if (TextUtils.isEmpty(Nationality)) {
                    Toast.makeText(getContext(), "Please enter your Nationality", Toast.LENGTH_LONG).show();
                    EditTextNationality.setError("Nationality is Required");
                    EditTextNationality.requestFocus();
                }else {
                    Gender = updateGender.getText().toString();
                    Username = edittextUsername.getText().toString();
                    Mobile = editTextMobile.getText().toString();
                    Nationality = EditTextNationality.getText().toString();

                    // Enter data to the firebase realtime.
                    ReadWriteUsersDetails writeUsersDetails = new ReadWriteUsersDetails(Mobile,Gender,Nationality);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                    String userID = firebaseUser.getUid();

                    databaseReference.child(userID).setValue(writeUsersDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(Username).build();
                                firebaseUser.updateProfile(profileUpdates);
                                Toast.makeText(getContext(),"Update Successful!",Toast.LENGTH_LONG).show();
                                FragmentTransaction fr = getFragmentManager().beginTransaction();
                                fr.replace(R.id.frame_layout,new UserFragment()).commit();
                            }
                        }
                    });
                }
            }
        });


        return v;
    }

    // ShowProfile Method
    private void showProfile(FirebaseUser firebaseUser) {
        String userID;

        String userIDofRegistered = firebaseUser.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUsersDetails readWriteUsersDetails = snapshot.getValue(ReadWriteUsersDetails.class);
                if (readWriteUsersDetails !=  null) {
                    Username = firebaseUser.getDisplayName();
                    Mobile = readWriteUsersDetails.Mobile;
                    Gender = readWriteUsersDetails.Gender;
                    Nationality = readWriteUsersDetails.Nationality;

                    edittextUsername.setText(Username);
                    editTextMobile.setText(Mobile);
                    EditTextNationality.setText(Nationality);

                    if (Gender.equals("Male")) {
                        updateGender = getView().findViewById(R.id.selectMale);
                    } else {
                        updateGender = getView().findViewById(R.id.selectFemale);

                    }
                    updateGender.setChecked(true);
                }else {
                    Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });


    }

}