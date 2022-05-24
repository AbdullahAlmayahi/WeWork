package com.example.wework;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.ViewHolder> {
    private ArrayList<AdvsRVModal> advsRVModalArrayList;
    private CookingServiceFragment context;
    private ElectronicMaintenanceFragment context2;
    private CarMaintenanceFragment context3;
    private PlumbingWorkFragment context4;
    private ElectricWorksFragment context5;
    private PaintsandDecorFragment context6;
    private OfficeworksFragment context7;
    private OnlineWorkFragment context8;
    private SectionSearch context9;

    private AdvertisementClickInterface advertisementClickInterface;

    public AdvertisementAdapter(ArrayList<AdvsRVModal> advsRVModalArrayList, CookingServiceFragment context, AdvertisementClickInterface advertisementClickInterface) {
        this.advsRVModalArrayList = advsRVModalArrayList;
        this.context = context;
        this.advertisementClickInterface = advertisementClickInterface;
    }

    public AdvertisementAdapter(ArrayList<AdvsRVModal> advsRVModalArrayList, ElectronicMaintenanceFragment context2, AdvertisementClickInterface advertisementClickInterface) {
        this.advsRVModalArrayList = advsRVModalArrayList;
        this.context2 = context2;
        this.advertisementClickInterface = advertisementClickInterface;
    }

    public AdvertisementAdapter(ArrayList<AdvsRVModal> advsRVModalArrayList, CarMaintenanceFragment context3, AdvertisementClickInterface advertisementClickInterface) {
        this.advsRVModalArrayList = advsRVModalArrayList;
        this.context3 = context3;
        this.advertisementClickInterface = advertisementClickInterface;
    }

    public AdvertisementAdapter(ArrayList<AdvsRVModal> advsRVModalArrayList, PlumbingWorkFragment context4, AdvertisementClickInterface advertisementClickInterface) {
        this.advsRVModalArrayList = advsRVModalArrayList;
        this.context4 = context4;
        this.advertisementClickInterface = advertisementClickInterface;
    }

    public AdvertisementAdapter(ArrayList<AdvsRVModal> advsRVModalArrayList, ElectricWorksFragment context5, AdvertisementClickInterface advertisementClickInterface) {
        this.advsRVModalArrayList = advsRVModalArrayList;
        this.context5 = context5;
        this.advertisementClickInterface = advertisementClickInterface;
    }

    public AdvertisementAdapter(ArrayList<AdvsRVModal> advsRVModalArrayList, PaintsandDecorFragment context6, AdvertisementClickInterface advertisementClickInterface) {
        this.advsRVModalArrayList = advsRVModalArrayList;
        this.context6 = context6;
        this.advertisementClickInterface = advertisementClickInterface;
    }

    public AdvertisementAdapter(ArrayList<AdvsRVModal> advsRVModalArrayList, OfficeworksFragment context7, AdvertisementClickInterface advertisementClickInterface) {
        this.advsRVModalArrayList = advsRVModalArrayList;
        this.context7 = context7;
        this.advertisementClickInterface = advertisementClickInterface;
    }

    public AdvertisementAdapter(ArrayList<AdvsRVModal> advsRVModalArrayList, OnlineWorkFragment context8, AdvertisementClickInterface advertisementClickInterface) {
        this.advsRVModalArrayList = advsRVModalArrayList;
        this.context8 = context8;
        this.advertisementClickInterface = advertisementClickInterface;
    }

    public AdvertisementAdapter(ArrayList<AdvsRVModal> advsRVModalArrayList, SectionSearch context9, AdvertisementClickInterface advertisementClickInterface) {
        this.advsRVModalArrayList = advsRVModalArrayList;
        this.context9 = context9;
        this.advertisementClickInterface = advertisementClickInterface;
    }

    @NonNull
    @Override
    public AdvertisementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AdvsRVModal advsRVModal = advsRVModalArrayList.get(position);
        holder.txtjobTitle.setText(advsRVModal.getTitleAdvs());
        holder.txtGender.setText(advsRVModal.getGenderAdvs());
        holder.txtjobType.setText(advsRVModal.getJob_TypeAdvs());
        holder.txtLocation.setText(advsRVModal.getLocationAdvs());
        holder.txtNationality.setText(advsRVModal.getNationalityAdvs());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advertisementClickInterface.onAdvertisementClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return advsRVModalArrayList.size();
    }

    public interface AdvertisementClickInterface {
        void onAdvertisementClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtjobTitle,txtGender,txtjobType,txtLocation,txtNationality;
        private ImageView imgGender,imgjobType,imgLocation,imgNationality;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtjobTitle = itemView.findViewById(R.id.txtjobTitle);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtjobType = itemView.findViewById(R.id.txtjobType);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtNationality = itemView.findViewById(R.id.txtNationality);
            imgGender = itemView.findViewById(R.id.imgGender);
            imgjobType = itemView.findViewById(R.id.imgjobType);
            imgLocation = itemView.findViewById(R.id.imgLocation);
            imgNationality = itemView.findViewById(R.id.imgNationality);

        }
    }

}
