package com.example.wework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText Email;
    Button Reset;
    ImageView Arrow_back;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Email = findViewById(R.id.inputEmail2);
        Reset = findViewById(R.id.btnReset);
        Arrow_back = findViewById(R.id.arrow_back);

        Arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this,Login.class));
            }
        });

        auth = FirebaseAuth.getInstance();

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswrod();
            }
        });

    }
    private void ResetPasswrod() {
        String EmailX = Email.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(EmailX)) {
            Toast.makeText(ForgotPassword.this, "Please enter your email", Toast.LENGTH_LONG).show();
            Email.setError("Email is Required");
            Email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(EmailX).matches()) {
            Toast.makeText(ForgotPassword.this, "Please Re-Enter your email", Toast.LENGTH_LONG).show();
            Email.setError("Valid email is Required");
            Email.requestFocus();
        } else {
            auth.sendPasswordResetEmail(EmailX).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPassword.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ForgotPassword.this, Login.class));

                    } else {
                        Toast.makeText(ForgotPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}