package com.example.wework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText inputUsername,inputEmail,inputPassword,inputConPassword,inputMobile, inputNationality;
    Button btnRegister;
    RadioButton updateGender;
    RadioGroup update_profile_gender;
    ImageView arrow_back;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConPassword = findViewById(R.id.inputConfirmPassword);
        inputMobile = findViewById(R.id.inputMobile);
        inputNationality = findViewById(R.id.inputNationality);

        btnRegister = findViewById(R.id.btnRegister);

        // Select Gender
        update_profile_gender = findViewById(R.id.update_profile_gender);
        update_profile_gender.clearCheck();

        // back arrow
        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,MainActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = inputUsername.getText().toString();
                String Email = inputEmail.getText().toString();
                String Password = inputPassword.getText().toString();
                String ConfirmPassword = inputConPassword.getText().toString();
                String Mobile = inputMobile.getText().toString();
                String Nationality = inputNationality.getText().toString();
                String Gender;

                int selectedGenderID = update_profile_gender.getCheckedRadioButtonId();
                updateGender = findViewById(selectedGenderID);



                // Check if user fill all the fields before sending data to firebase
                if (TextUtils.isEmpty(Username)) {
                    Toast.makeText(Register.this, "Please enter your Username", Toast.LENGTH_LONG).show();
                    inputUsername.setError("Username is Required");
                    inputUsername.requestFocus();
                } else if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(Register.this, "Please enter your Email", Toast.LENGTH_LONG).show();
                    inputEmail.setError("Email is Required");
                    inputEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(Register.this, "Please Re-enter your Email", Toast.LENGTH_LONG).show();
                    inputEmail.setError("Valid email is Required");
                    inputEmail.requestFocus();
                } else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(Register.this, "Please enter your Password", Toast.LENGTH_LONG).show();
                    inputPassword.setError("Password is Required");
                    inputPassword.requestFocus();
                } else if (TextUtils.isEmpty(ConfirmPassword)) {
                    Toast.makeText(Register.this, "Please enter your Confirm Password", Toast.LENGTH_LONG).show();
                    inputConPassword.setError("Confirm Password is Required");
                    inputConPassword.requestFocus();
                } else if (TextUtils.isEmpty(Mobile)) {
                    Toast.makeText(Register.this, "Please enter your Mobile number", Toast.LENGTH_LONG).show();
                    inputMobile.setError("Mobile number is Required");
                    inputMobile.requestFocus();
                } else if (Mobile.length() != 8) {
                    Toast.makeText(Register.this, "Please re-enter your Mobile number", Toast.LENGTH_LONG).show();
                    inputMobile.setError("Mobile number is Should be 8 digits");
                    inputMobile.requestFocus();
                } else if (update_profile_gender.getCheckedRadioButtonId()==-1){
                    Toast.makeText(Register.this, "Please select your gender", Toast.LENGTH_LONG).show();
                    updateGender.setError("Gender is required");
                    updateGender.requestFocus();
                } else if (TextUtils.isEmpty(Nationality)) {
                    Toast.makeText(Register.this, "Please enter your Nationality", Toast.LENGTH_LONG).show();
                    inputNationality.setError("Nationality is Required");
                    inputNationality.requestFocus();
                } else if (!Password.equals(ConfirmPassword)) {
                    Toast.makeText(Register.this, "Password not matching", Toast.LENGTH_LONG).show();inputConPassword.setError("Password Confirmation is Required");
                    inputPassword.requestFocus();
                    inputConPassword.requestFocus();
                } else {
                    Gender = updateGender.getText().toString();
                    RegisterUser(Username, Email, Password, Mobile, Gender, Nationality);
                }
            }
        });

        findViewById(R.id.textLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Login(); }
        });
    }


    public void Login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void RegisterUser(String Username,String Email, String Password, String Mobile, String Gender,String Nationality) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(Username).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    ReadWriteUsersDetails writeUsersDetails = new ReadWriteUsersDetails(Mobile,Gender,Nationality);

                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users");
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUsersDetails).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                firebaseUser.sendEmailVerification();
                                Toast.makeText(Register.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Register.this,Login.class));

                            } else {
                                Toast.makeText(Register.this, "User Registered failed, please try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthUserCollisionException e){
                        Toast.makeText(Register.this, "User is already registered with this email", Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}