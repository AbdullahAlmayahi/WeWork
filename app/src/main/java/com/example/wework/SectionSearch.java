package com.example.wework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Locale;

public class SectionSearch extends AppCompatActivity implements AdvertisementAdapter.AdvertisementClickInterface {

    RecyclerView AdvertisementRV, search_recycler_view;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;
    RelativeLayout idRLBSheet;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ArrayList<AdvsRVModal> advsRVModalArrayList;
    AdvertisementAdapter advertisementAdapter;
    ImageView arrow_back;

    String [] Section = {"All","Cooking Service","Electronic Maintenance","Car Maintenance",
            "Plumbing Work","Electric Works","Paints and Decor","Officeworks","Online Work"};

    String [] Gender = {"Not Specified", "Male", "Female"};

    String [] JobType = {"All", "Part Time", "Period","Temporary"};

    String [] Location = {"All", "Muscat", "Al Batinah North", "Al Batinah South", "Ad Dakhiliyah",
            "Ad Dhahirah","Al Wusta","Ash Sharqiyah North","Ash Sharqiyah South",
            "Buraymi","Dofar","Musandam"};

    String [] Nationality = {"Any Nationality", "Omani"};

    AutoCompleteTextView autoCompleteTextViewSection,autoCompleteTextViewGender,autoCompleteTextViewJobType,autoCompleteTextViewLocation,autoCompleteTextViewNationality;
    com.google.android.material.textfield.TextInputLayout sectionLayout,GenderLayout,JobTypeLayout,LocationLayout,NationalityLayout;
    TextView txtSection,textGender,txt_Job_Type,txt_Location,txt_Nationality;
    ArrayAdapter<String> adaptertItems;
    Button btnSearch;
    RecyclerView idAdvertisementRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_search);

        //Selection
        //Section
        autoCompleteTextViewSection = findViewById(R.id.autoCompleteTextViewSection);
        adaptertItems = new ArrayAdapter<String>(this,R.layout.dropdown_item,Section);
        autoCompleteTextViewSection.setAdapter(adaptertItems);

        //Gender
        autoCompleteTextViewGender = findViewById(R.id.autoCompleteTextViewGender);
        adaptertItems = new ArrayAdapter<String>(this,R.layout.dropdown_item,Gender);
        autoCompleteTextViewGender.setAdapter(adaptertItems);

        //Job Type
        autoCompleteTextViewJobType = findViewById(R.id.autoCompleteTextViewJobType);
        adaptertItems = new ArrayAdapter<String>(this,R.layout.dropdown_item,JobType);
        autoCompleteTextViewJobType.setAdapter(adaptertItems);

        //Location
        autoCompleteTextViewLocation = findViewById(R.id.autoCompleteTextViewLocation);
        adaptertItems = new ArrayAdapter<String>(this,R.layout.dropdown_item,Location);
        autoCompleteTextViewLocation.setAdapter(adaptertItems);

        //Nationality
        autoCompleteTextViewNationality = findViewById(R.id.autoCompleteTextViewNationality);
        adaptertItems = new ArrayAdapter<String>(this,R.layout.dropdown_item,Nationality);
        autoCompleteTextViewNationality.setAdapter(adaptertItems);


        // Defined val
        sectionLayout = findViewById(R.id.sectionLayout);
        GenderLayout = findViewById(R.id.GenderLayout);
        JobTypeLayout = findViewById(R.id.JobTypeLayout);
        LocationLayout = findViewById(R.id.LocationLayout);
        NationalityLayout = findViewById(R.id.NationalityLayout);
        txtSection = findViewById(R.id.txtSection);
        textGender = findViewById(R.id.textGender);
        txt_Job_Type = findViewById(R.id.txt_Job_Type);
        txt_Location = findViewById(R.id.txt_Location);
        txt_Nationality = findViewById(R.id.txt_Nationality);
        btnSearch = findViewById(R.id.btn_Search);
        idAdvertisementRV = findViewById(R.id.idAdvertisementRV);

        // back arrow
        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SectionSearch.this,Search.class));
            }
        });


        // Press Search
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String SectionI = autoCompleteTextViewSection.getText().toString();
                String GenderI = autoCompleteTextViewGender.getText().toString();
                String JobTypeI = autoCompleteTextViewJobType.getText().toString();
                String LocationI = autoCompleteTextViewLocation.getText().toString();
                String NationalityI = autoCompleteTextViewNationality.getText().toString();

                 autoCompleteTextViewSection.setVisibility(View.GONE);
                 sectionLayout.setVisibility(View.GONE);
                 txtSection.setVisibility(View.GONE);
                 autoCompleteTextViewGender.setVisibility(View.GONE);
                 autoCompleteTextViewJobType.setVisibility(View.GONE);
                 autoCompleteTextViewLocation.setVisibility(View.GONE);
                 autoCompleteTextViewNationality.setVisibility(View.GONE);
                 GenderLayout.setVisibility(View.GONE);
                 JobTypeLayout.setVisibility(View.GONE);
                 LocationLayout.setVisibility(View.GONE);
                 NationalityLayout.setVisibility(View.GONE);
                 textGender.setVisibility(View.GONE);
                 txt_Job_Type.setVisibility(View.GONE);
                 txt_Location.setVisibility(View.GONE);
                 txt_Nationality.setVisibility(View.GONE);
                 btnSearch.setVisibility(View.GONE);
                 idAdvertisementRV.setVisibility(View.VISIBLE);
                 getAllAdvertisement(SectionI,GenderI,JobTypeI,LocationI,NationalityI);
            }
        });

        TextView textView = findViewById(R.id.titleSearch);
        String username = "Username not set";
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
        }
        textView.setText(username);

        if (username.equals("Section")){
            autoCompleteTextViewGender.setVisibility(View.GONE);
            autoCompleteTextViewJobType.setVisibility(View.GONE);
            autoCompleteTextViewLocation.setVisibility(View.GONE);
            autoCompleteTextViewNationality.setVisibility(View.GONE);
            GenderLayout.setVisibility(View.GONE);
            JobTypeLayout.setVisibility(View.GONE);
            LocationLayout.setVisibility(View.GONE);
            NationalityLayout.setVisibility(View.GONE);
            textGender.setVisibility(View.GONE);
            txt_Job_Type.setVisibility(View.GONE);
            txt_Location.setVisibility(View.GONE);
            txt_Nationality.setVisibility(View.GONE);
        } else if (username.equals("Gender")) {
            autoCompleteTextViewSection.setVisibility(View.GONE);
            autoCompleteTextViewJobType.setVisibility(View.GONE);
            autoCompleteTextViewLocation.setVisibility(View.GONE);
            autoCompleteTextViewNationality.setVisibility(View.GONE);
            sectionLayout.setVisibility(View.GONE);
            JobTypeLayout.setVisibility(View.GONE);
            LocationLayout.setVisibility(View.GONE);
            NationalityLayout.setVisibility(View.GONE);
            txtSection.setVisibility(View.GONE);
            txt_Job_Type.setVisibility(View.GONE);
            txt_Location.setVisibility(View.GONE);
            txt_Nationality.setVisibility(View.GONE);
        }else if (username.equals("Job Type")) {
            autoCompleteTextViewSection.setVisibility(View.GONE);
            autoCompleteTextViewGender.setVisibility(View.GONE);
            autoCompleteTextViewLocation.setVisibility(View.GONE);
            autoCompleteTextViewNationality.setVisibility(View.GONE);
            sectionLayout.setVisibility(View.GONE);
            GenderLayout.setVisibility(View.GONE);
            LocationLayout.setVisibility(View.GONE);
            NationalityLayout.setVisibility(View.GONE);
            txtSection.setVisibility(View.GONE);
            textGender.setVisibility(View.GONE);
            txt_Location.setVisibility(View.GONE);
            txt_Nationality.setVisibility(View.GONE);
        }else if (username.equals("Location")) {
            autoCompleteTextViewSection.setVisibility(View.GONE);
            autoCompleteTextViewGender.setVisibility(View.GONE);
            autoCompleteTextViewJobType.setVisibility(View.GONE);
            autoCompleteTextViewNationality.setVisibility(View.GONE);
            sectionLayout.setVisibility(View.GONE);
            GenderLayout.setVisibility(View.GONE);
            JobTypeLayout.setVisibility(View.GONE);
            NationalityLayout.setVisibility(View.GONE);
            txtSection.setVisibility(View.GONE);
            textGender.setVisibility(View.GONE);
            txt_Job_Type.setVisibility(View.GONE);
            txt_Nationality.setVisibility(View.GONE);
        }else if (username.equals("Nationality")) {
            autoCompleteTextViewSection.setVisibility(View.GONE);
            autoCompleteTextViewGender.setVisibility(View.GONE);
            autoCompleteTextViewLocation.setVisibility(View.GONE);
            autoCompleteTextViewJobType.setVisibility(View.GONE);
            sectionLayout.setVisibility(View.GONE);
            GenderLayout.setVisibility(View.GONE);
            LocationLayout.setVisibility(View.GONE);
            JobTypeLayout.setVisibility(View.GONE);
            txtSection.setVisibility(View.GONE);
            textGender.setVisibility(View.GONE);
            txt_Location.setVisibility(View.GONE);
            txt_Job_Type.setVisibility(View.GONE);
        }

    }
    public void getAllAdvertisement(String SectionI,String GenderI,String JobTypeI,String LocationI, String NationalityI){


        // showing advertisements
        AdvertisementRV = findViewById(R.id.idAdvertisementRV);
        advsRVModalArrayList = new ArrayList<>();
        advertisementAdapter = new AdvertisementAdapter(advsRVModalArrayList,this,this);
        AdvertisementRV.setLayoutManager(new LinearLayoutManager(this));
        AdvertisementRV.setAdapter(advertisementAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Advertisements");

        // seraching section
        if (SectionI.equals("Cooking Service") ) {
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("sectionAdvs").equalTo("Cooking Service");
            query.addListenerForSingleValueEvent(valueEventListener);
        } else if (SectionI.equals("Electronic Maintenance")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("sectionAdvs").equalTo("Electronic Maintenance");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (SectionI.equals("Car Maintenance")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("sectionAdvs").equalTo("Car Maintenance");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (SectionI.equals("Plumbing Work")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("sectionAdvs").equalTo("Plumbing Work");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (SectionI.equals("Electric Works")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("sectionAdvs").equalTo("Electric Works");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (SectionI.equals("Paints and Decor")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("sectionAdvs").equalTo("Paints and Decor");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (SectionI.equals("Officeworks")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("sectionAdvs").equalTo("Officeworks");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (SectionI.equals("Online Work")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("sectionAdvs").equalTo("Online Work");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (SectionI.equals("All")){
            databaseReference = FirebaseDatabase.getInstance().getReference("Advertisements");
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }

        // Searching Gender
        else if (GenderI.equals("Not Specified")){
            databaseReference = FirebaseDatabase.getInstance().getReference("Advertisements");
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }else if (GenderI.equals("Male")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("genderAdvs").equalTo("Male");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (GenderI.equals("Female")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("genderAdvs").equalTo("Female");
            query.addListenerForSingleValueEvent(valueEventListener);
        }

        // Searching Job Type
        else if (JobTypeI.equals("All")) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Advertisements");
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }else if (JobTypeI.equals("Part Time")) {
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("job_TypeAdvs").equalTo("Part Time");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (JobTypeI.equals("Period")) {
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("job_TypeAdvs").equalTo("Period");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (JobTypeI.equals("Temporary")) {
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("job_TypeAdvs").equalTo("Temporary");
            query.addListenerForSingleValueEvent(valueEventListener);
        }

        // Searching Location
        else if (LocationI.equals("Muscat")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Muscat");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("Al Batinah North")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Al Batinah North");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("Al Batinah South")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Al Batinah South");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("Ad Dakhiliyah")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Ad Dakhiliyah");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("Ad Dhahirah")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Ad Dhahirah");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("Al Wusta")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Al Wusta");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("Ash Sharqiyah North")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Ash Sharqiyah North");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("Ash Sharqiyah South")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Ash Sharqiyah South");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("Buraymi")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Buraymi");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("Dofar")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Dofar");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("Musandam")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("locationAdvs").equalTo("Musandam");
            query.addListenerForSingleValueEvent(valueEventListener);
        }else if (LocationI.equals("All")){
            databaseReference = FirebaseDatabase.getInstance().getReference("Advertisements");
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }

        // Searching Nationality

        else if (NationalityI.equals("Any Nationality")){
            databaseReference = FirebaseDatabase.getInstance().getReference("Advertisements");
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }else if (NationalityI.equals("Omani")){
            Query query = FirebaseDatabase.getInstance().getReference("Advertisements")
                    .orderByChild("nationalityAdvs").equalTo("Omani");
            query.addListenerForSingleValueEvent(valueEventListener);
        }
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            advsRVModalArrayList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AdvsRVModal artist = snapshot.getValue(AdvsRVModal.class);
                    advsRVModalArrayList.add(artist);
                }
                advertisementAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    @Override
    public void onAdvertisementClick(int position) {
        displayBottomSheet(advsRVModalArrayList.get(position));

    }

    private void displayBottomSheet(AdvsRVModal advsRVModal){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SectionSearch.this);
        View layout = LayoutInflater.from(SectionSearch.this).inflate(R.layout.open_advs,idRLBSheet);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView TitleX = layout.findViewById(R.id.TitleX);
        TextView inputGenderX = layout.findViewById(R.id.inputGenderX);
        TextView inputJobTypeX = layout.findViewById(R.id.inputJobTypeX);
        TextView inputLocationX = layout.findViewById(R.id.inputLocationX);
        TextView inputSalaryX = layout.findViewById(R.id.inputSalaryX);
        TextView inputNationalityX = layout.findViewById(R.id.inputNationalityX);
        TextView DescX = layout.findViewById(R.id.DescX);
        Button Apply = layout.findViewById(R.id.btn_apply);

        TitleX.setText(advsRVModal.getTitleAdvs());
        inputGenderX.setText(advsRVModal.getGenderAdvs());
        inputJobTypeX.setText(advsRVModal.getJob_TypeAdvs());
        inputLocationX.setText(advsRVModal.getLocationAdvs());
        inputSalaryX.setText(advsRVModal.getSalaryAdvs()+" OMR");
        inputNationalityX.setText(advsRVModal.getNationalityAdvs());
        DescX.setText(advsRVModal.getDescriptionAdvs());


        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SectionSearch.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(advsRVModal.getMobileAdvs(),null,"Apply for the job"+advsRVModal.getTitleAdvs(),null,null);
                    Toast.makeText(SectionSearch.this,"you applied for the job successfully",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SectionSearch.this,HomePage.class);
                    startActivity(intent);

                } else {
                    ActivityCompat.requestPermissions(SectionSearch.this,new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });
    }
}
