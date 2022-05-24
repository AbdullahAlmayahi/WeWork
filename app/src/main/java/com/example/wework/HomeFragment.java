package com.example.wework;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {

    ImageView ic_filter;
    LinearLayout layoutCookingService,layoutEletronicMaint,layoutCarMaint,layoutPlumbingWork,layoutElectricWorks,layoutPaintsDecor,layoutOfficeWorks,layoutOnlineWorks;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        layoutCookingService = v.findViewById(R.id.layoutCookingService);
        layoutCookingService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout,new CookingServiceFragment()).commit();
            }
        });
        layoutEletronicMaint = v.findViewById(R.id.layoutEletronicMaint);
        layoutEletronicMaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout,new ElectronicMaintenanceFragment()).commit();
            }
        });

        layoutCarMaint = v.findViewById(R.id.layoutCarMaint);
        layoutCarMaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout,new CarMaintenanceFragment()).commit();
            }
        });

        layoutPlumbingWork = v.findViewById(R.id.layoutPlumbingWork);
        layoutPlumbingWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout,new PlumbingWorkFragment()).commit();
            }
        });

        layoutElectricWorks = v.findViewById(R.id.layoutElectricWorks);
        layoutElectricWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout,new ElectricWorksFragment()).commit();
            }
        });

        layoutPaintsDecor = v.findViewById(R.id.layoutPaintsDecor);
        layoutPaintsDecor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout,new PaintsandDecorFragment()).commit();
            }
        });

        layoutOfficeWorks = v.findViewById(R.id.layoutOfficeWorks);
        layoutOfficeWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout,new OfficeworksFragment()).commit();
            }
        });

        layoutOnlineWorks = v.findViewById(R.id.layoutOnlineWorks);
        layoutOnlineWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout,new OnlineWorkFragment()).commit();
            }
        });


        ic_filter = v.findViewById(R.id.ic_filter);
        ic_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Search.class);
                startActivity(intent);
            }
        });

        return v;
    }
}

