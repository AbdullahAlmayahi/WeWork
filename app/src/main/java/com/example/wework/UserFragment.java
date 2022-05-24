package com.example.wework;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {

    TextView textview_show_welcome, textview_show_full_name,textview_show_Email,textview_show_Mobile,textview_show_Gender,
             textview_show_Nationality;
    Button UpdateProfile , Logoff;
    private String Username, Email,Mobile,Gender,Nationality;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        textview_show_welcome = v.findViewById(R.id.textview_show_welcome);
        textview_show_full_name = v.findViewById(R.id.textview_show_full_name);
        textview_show_Email = v.findViewById(R.id.textview_show_email);
        textview_show_Mobile = v.findViewById(R.id.textview_show_mobile);
        textview_show_Gender = v.findViewById(R.id.textview_show_Gender);
        textview_show_Nationality = v.findViewById(R.id.textview_show_Nationality);
        UpdateProfile = v.findViewById(R.id.btnUpdateProfile);
        Logoff = v.findViewById(R.id.btnLogoff);

        UpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout,new UpdateProfile()).commit();
            }
        });

        Logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(),"Logged off Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(),Login.class));
            }
        });


        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser == null){
            Toast.makeText(getContext(),"Something went wrong! User's details are not available at the moment",
                    Toast.LENGTH_LONG).show();
        }else {
            showUserProfile(firebaseUser);
        }



        return v;
    }

    private void showUserProfile(FirebaseUser firebaseUser) {

        String userID = firebaseUser.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUsersDetails readUserDetails = snapshot.getValue(ReadWriteUsersDetails.class);
                if (readUserDetails != null){
                    Username = firebaseUser.getDisplayName();
                    Email =firebaseUser.getEmail();
                    Gender = readUserDetails.Gender;
                    Mobile = readUserDetails.Mobile;
                    Nationality = readUserDetails.Nationality;

                    textview_show_welcome.setText("Welcome, "+Username+ "!");
                    textview_show_full_name.setText(Username);
                    textview_show_Email.setText(Email);
                    textview_show_Mobile.setText(Mobile);
                    textview_show_Gender.setText(Gender);
                    textview_show_Nationality.setText(Nationality);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });
    }
}