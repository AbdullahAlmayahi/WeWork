package com.example.wework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Login(); }
        });

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { SignUp(); }
        });
    }
    public void openHomePage (){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
    public void Login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void SignUp() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}