package com.example.wework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

     EditText inputEmail , inputPassword;
     FirebaseAuth authProfile;
     Button btnLogin;
     ImageView arrow_back;
     TextView Forgetpaasword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Forgetpaasword = findViewById(R.id.forgotPassword);
        inputEmail = findViewById(R.id.inputEmail2);
        inputPassword = findViewById(R.id.inputPassword2);
        authProfile = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnlogin2);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = inputEmail.getText().toString();
                String Password = inputPassword.getText().toString();

                // Validation
                if (TextUtils.isEmpty(Email)){
                    Toast.makeText(Login.this,"Please enter your email",Toast.LENGTH_LONG).show();
                    inputEmail.setError("Email is Required");
                    inputEmail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    Toast.makeText(Login.this,"Please Re-Enter your email",Toast.LENGTH_LONG).show();
                    inputEmail.setError("Valid email is Required");
                    inputEmail.requestFocus();
                }else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(Login.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    inputPassword.setError("Password is Required");
                    inputPassword.requestFocus();
                }else {
                    loginuser(Email,Password);
                }


            }
        });

        // back arrow
        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,MainActivity.class));
            }
        });

        Forgetpaasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgotPassword.class));
            }
        });


        findViewById(R.id.textSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { SignUp();
            }
        });
    }
    public void SignUp() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void loginuser (String Email,String Password){
        authProfile.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "you are logged in now", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login.this,HomePage.class));

                }else {

                    Toast.makeText(Login.this, "Wrong Email or Password", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}