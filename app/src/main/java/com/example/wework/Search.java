package com.example.wework;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;


public class Search extends AppCompatActivity {

    private MainAdapter.RecyclerViewClickListener listener;
    RecyclerView search_recycler_view;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;

    ImageView arrow_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // back arrow
        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Search.this,HomePage.class));
            }
        });


        // add selection buttons
        search_recycler_view = findViewById(R.id.search_recycler_view);
        String[] longName = {"Section", "Gender", "Job Type", "Location", "Nationality"};

        mainModels = new ArrayList<>();
        for (int i = 0; i < longName.length; i++) {
            MainModel model = new MainModel(longName[i]);
            mainModels.add(model);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                Search.this, LinearLayoutManager.VERTICAL, false
        );
        search_recycler_view.setLayoutManager(layoutManager);
        search_recycler_view.setItemAnimator(new DefaultItemAnimator());

        setOnClickListner();
        mainAdapter = new MainAdapter(Search.this, mainModels, listener);
        search_recycler_view.setAdapter(mainAdapter);
        //
    }
    private void setOnClickListner() {
        listener = new MainAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), SectionSearch.class);
                intent.putExtra("username", mainModels.get(position).getLangName());
                startActivity(intent);
            }
        };
    }
}