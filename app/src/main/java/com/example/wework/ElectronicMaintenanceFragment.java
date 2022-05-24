package com.example.wework;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class ElectronicMaintenanceFragment extends Fragment implements AdvertisementAdapter.AdvertisementClickInterface{

    RecyclerView AdvertisementRV;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<AdvsRVModal> advsRVModalArrayList;
    AdvertisementAdapter advertisementAdapter;
    ImageView arrow_back;
    RelativeLayout idRLBSheet;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_electronic_maintenance, container, false);

        // showing advertisement
        idRLBSheet = v.findViewById(R.id.idRLBSheet);
        AdvertisementRV = v.findViewById(R.id.idAdvertisementRV);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Advertisements");
        advsRVModalArrayList = new ArrayList<>();
        advertisementAdapter = new AdvertisementAdapter(advsRVModalArrayList,this,this);
        AdvertisementRV.setLayoutManager(new LinearLayoutManager(getContext()));
        AdvertisementRV.setAdapter(advertisementAdapter);

        getAllAdvertisement();

        arrow_back = v.findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),HomePage.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void getAllAdvertisement(){

        advsRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                AdvsRVModal SectionData = snapshot.getValue(AdvsRVModal.class);
                advertisementAdapter.notifyDataSetChanged();
                if (SectionData.getSectionAdvs().equals("Electronic Maintenance")){
                    advsRVModalArrayList.add(SectionData);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                AdvsRVModal SectionData = snapshot.getValue(AdvsRVModal.class);
                advertisementAdapter.notifyDataSetChanged();
                if (SectionData.getSectionAdvs().equals("Electronic Maintenance")){
                    advsRVModalArrayList.add(SectionData);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                AdvsRVModal SectionData = snapshot.getValue(AdvsRVModal.class);
                advertisementAdapter.notifyDataSetChanged();
                if (SectionData.getSectionAdvs().equals("Electronic Maintenance")){
                    advsRVModalArrayList.add(SectionData);
                }
            }



            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                AdvsRVModal SectionData = snapshot.getValue(AdvsRVModal.class);
                advertisementAdapter.notifyDataSetChanged();
                if (SectionData.getSectionAdvs().equals("Electronic Maintenance")){
                    advsRVModalArrayList.add(SectionData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        advertisementAdapter.notifyDataSetChanged();
        AdvertisementRV.setAdapter(advertisementAdapter);

    }

    @Override
    public void onAdvertisementClick(int position) {
        displayBottomSheet(advsRVModalArrayList.get(position));
    }

    private void displayBottomSheet(AdvsRVModal advsRVModal){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.open_advs,idRLBSheet);
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
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(advsRVModal.getMobileAdvs(),null,"Apply for the job"+advsRVModal.getTitleAdvs(),null,null);
                    Toast.makeText(getContext(),"you applied for the job successfully",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(),HomePage.class);
                    startActivity(intent);

                } else {
                    ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });
    }
}