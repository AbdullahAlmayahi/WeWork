package com.example.wework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddAdvertisement extends AppCompatActivity {

    String[] SectionX = {"Cooking Service", "Electronic Maintenance", "Car Maintenance",
            "Plumbing Work", "Electric Works", "Paints and Decor", "Officeworks", "Online Work"};

    String[] GenderX = {"Not Specified", "Male", "Female"};

    String[] JobTypeX = {"Part Time", "Period", "Temporary"};

    String[] LocationX = {"Muscat", "Al Batinah North", "Al Batinah South", "Ad Dakhiliyah",
            "Ad Dhahirah", "Al Wusta", "Ash Sharqiyah North", "Ash Sharqiyah South",
            "Buraymi", "Dofar", "Musandam"};

    String[] NationalityX = {"Any Nationality", "Omani"};

    AutoCompleteTextView autoCompleteTextViewSection;
    AutoCompleteTextView autoCompleteTextViewGender;
    AutoCompleteTextView autoCompleteTextViewJobType;
    AutoCompleteTextView autoCompleteTextViewLocation;
    AutoCompleteTextView autoCompleteTextViewNationality;
    ArrayAdapter<String> adaptertItems;


    EditText Title, Mobile , Salary, inputDesc;
    AutoCompleteTextView Section, Gender, Job_Type, Location, Nationality;
    Button Add;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String AdvsID;
    ImageView arrow_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advertisement);

        //Section
        autoCompleteTextViewSection = findViewById(R.id.autoCompleteTextViewSection);
        adaptertItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, SectionX);
        autoCompleteTextViewSection.setAdapter(adaptertItems);

        //Gender
        autoCompleteTextViewGender = findViewById(R.id.autoCompleteTextViewGender);
        adaptertItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, GenderX);
        autoCompleteTextViewGender.setAdapter(adaptertItems);

        //Job Type
        autoCompleteTextViewJobType = findViewById(R.id.autoCompleteTextViewJobType);
        adaptertItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, JobTypeX);
        autoCompleteTextViewJobType.setAdapter(adaptertItems);

        //Location
        autoCompleteTextViewLocation = findViewById(R.id.autoCompleteTextViewLocation);
        adaptertItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, LocationX);
        autoCompleteTextViewLocation.setAdapter(adaptertItems);

        //Nationality
        autoCompleteTextViewNationality = findViewById(R.id.autoCompleteTextViewNationality);
        adaptertItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, NationalityX);
        autoCompleteTextViewNationality.setAdapter(adaptertItems);


        Title = findViewById(R.id.TitleX);
        Mobile = findViewById(R.id.inputMobileX);
        Section = findViewById(R.id.autoCompleteTextViewSection);
        Gender = findViewById(R.id.autoCompleteTextViewGender);
        Job_Type = findViewById(R.id.autoCompleteTextViewJobType);
        Location = findViewById(R.id.autoCompleteTextViewLocation);
        Nationality = findViewById(R.id.autoCompleteTextViewNationality);
        inputDesc = findViewById(R.id.DescX);
        Salary = findViewById(R.id.inputSalary);
        Add = findViewById(R.id.btn_adding);
        arrow_back = findViewById(R.id.arrow_back);

        // onclick send back to homepage
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAdvertisement.this, HomePage.class));
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Advertisements");

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TitleAdvs = Title.getText().toString();
                String MobileAdvs = Mobile.getText().toString();
                String SectionAdvs = Section.getText().toString();
                String GenderAdvs = Gender.getText().toString();
                String Job_TypeAdvs = Job_Type.getText().toString();
                String LocationAdvs = Location.getText().toString();
                String NationalityAdvs = Nationality.getText().toString();
                String DescriptionAdvs = inputDesc.getText().toString();
                String SalaryAdvs = Salary.getText().toString();
                AdvsID = TitleAdvs;


                // Check if user fill all the fields before sending data to firebase
                if (TextUtils.isEmpty(SectionAdvs)) {
                    Toast.makeText(AddAdvertisement.this, "Please select the Section", Toast.LENGTH_LONG).show();
                    Section.setError("Section is Required");
                    Section.requestFocus();
                } else if (TextUtils.isEmpty(TitleAdvs)) {
                    Toast.makeText(AddAdvertisement.this, "Please Enter the title", Toast.LENGTH_LONG).show();
                    Title.setError("Title is Required");
                    Title.requestFocus();
                } else if (TextUtils.isEmpty(GenderAdvs)) {
                    Toast.makeText(AddAdvertisement.this, "Please select the Gender", Toast.LENGTH_LONG).show();
                    Gender.setError("Gender is Required");
                    Gender.requestFocus();
                }  else if (TextUtils.isEmpty(Job_TypeAdvs)) {
                    Toast.makeText(AddAdvertisement.this, "Please select the Job Type", Toast.LENGTH_LONG).show();
                    Job_Type.setError("Job Type is Required");
                    Job_Type.requestFocus();
                }  else if (TextUtils.isEmpty(LocationAdvs)) {
                    Toast.makeText(AddAdvertisement.this, "Please select the Location", Toast.LENGTH_LONG).show();
                    Location.setError("Location is Required");
                    Location.requestFocus();
                } else if (TextUtils.isEmpty(NationalityAdvs)){
                    Toast.makeText(AddAdvertisement.this, "Please select the Nationality", Toast.LENGTH_LONG).show();
                    Nationality.setError("Nationality is Required");
                    Nationality.requestFocus();
                }else if (TextUtils.isEmpty(SalaryAdvs)) {
                    Toast.makeText(AddAdvertisement.this, "Please select the Salary", Toast.LENGTH_LONG).show();
                    Salary.setError("Salary is Required");
                    Salary.requestFocus();
                } else if (TextUtils.isEmpty(MobileAdvs)) {
                    Toast.makeText(AddAdvertisement.this, "Please enter your Mobile number", Toast.LENGTH_LONG).show();
                    Mobile.setError("Mobile number is Required");
                    Mobile.requestFocus();
                } else if (MobileAdvs.length() != 8) {
                    Toast.makeText(AddAdvertisement.this, "Please re-enter your Mobile number", Toast.LENGTH_LONG).show();
                    Mobile.setError("Mobile number is Should be 8 digits");
                    Mobile.requestFocus();
                } else {
                    AdvsRVModal advsRVModal = new AdvsRVModal(TitleAdvs, MobileAdvs, SectionAdvs, GenderAdvs, Job_TypeAdvs, LocationAdvs, NationalityAdvs, SalaryAdvs, DescriptionAdvs , AdvsID);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(AdvsID).setValue(advsRVModal);
                            Toast.makeText(AddAdvertisement.this, "Advertisement Added", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AddAdvertisement.this, HomePage.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddAdvertisement.this, "Error is " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}

