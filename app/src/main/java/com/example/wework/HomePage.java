package com.example.wework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    UserFragment userFragment = new UserFragment();
    MoreFragment moreFragment = new MoreFragment();
    com.google.android.material.floatingactionbutton.FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnAdd = findViewById(R.id.btnAddN);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,AddAdvertisement.class));
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuHome:
                        btnAdd.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment).commit();
                        return true;
                    case R.id.menuUser:
                        btnAdd.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, userFragment).commit();
                        return true;
                    case R.id.menuMore:
                        btnAdd.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, moreFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}