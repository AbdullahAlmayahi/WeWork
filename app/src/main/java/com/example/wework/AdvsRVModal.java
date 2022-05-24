package com.example.wework;

import android.os.Parcel;
import android.os.Parcelable;

public class AdvsRVModal implements Parcelable{

    private String TitleAdvs;
    private String MobileAdvs;
    private String SectionAdvs;
    private String GenderAdvs;
    private String Job_TypeAdvs;
    private String LocationAdvs;
    private String NationalityAdvs;
    private String SalaryAdvs;
    private String DescriptionAdvs;
    private String AdvsID;

    public AdvsRVModal (){

    }

    protected AdvsRVModal(Parcel in) {
        TitleAdvs = in.readString();
        MobileAdvs = in.readString();
        SectionAdvs = in.readString();
        GenderAdvs = in.readString();
        Job_TypeAdvs = in.readString();
        LocationAdvs = in.readString();
        NationalityAdvs = in.readString();
        SalaryAdvs = in.readString();
        DescriptionAdvs = in.readString();
        AdvsID = in.readString();
    }

    public static final Creator<AdvsRVModal> CREATOR = new Creator<AdvsRVModal>() {
        @Override
        public AdvsRVModal createFromParcel(Parcel in) {
            return new AdvsRVModal(in);
        }

        @Override
        public AdvsRVModal[] newArray(int size) {
            return new AdvsRVModal[size];
        }
    };

    public String getTitleAdvs() {
        return TitleAdvs;
    }

    public void setTitleAdvs(String titleAdvs) {
        TitleAdvs = titleAdvs;
    }

    public String getMobileAdvs() {
        return MobileAdvs;
    }

    public void setMobileAdvs(String mobileAdvs) {
        MobileAdvs = mobileAdvs;
    }

    public String getSectionAdvs() {
        return SectionAdvs;
    }

    public void setSectionAdvs(String sectionAdvs) {
        SectionAdvs = sectionAdvs;
    }

    public String getGenderAdvs() {
        return GenderAdvs;
    }

    public void setGenderAdvs(String genderAdvs) {
        GenderAdvs = genderAdvs;
    }

    public String getJob_TypeAdvs() {
        return Job_TypeAdvs;
    }

    public void setJob_TypeAdvs(String job_TypeAdvs) {
        Job_TypeAdvs = job_TypeAdvs;
    }

    public String getLocationAdvs() {
        return LocationAdvs;
    }

    public void setLocationAdvs(String locationAdvs) {
        LocationAdvs = locationAdvs;
    }

    public String getNationalityAdvs() {
        return NationalityAdvs;
    }

    public void setNationalityAdvs(String nationalityAdvs) {
        NationalityAdvs = nationalityAdvs;
    }

    public String getSalaryAdvs() {
        return SalaryAdvs;
    }

    public void setSalaryAdvs(String salaryAdvs) {
        SalaryAdvs = salaryAdvs;
    }

    public String getDescriptionAdvs() {
        return DescriptionAdvs;
    }

    public void setDescriptionAdvs(String descriptionAdvs) {
        DescriptionAdvs = descriptionAdvs;
    }

    public String getAdvsID() {
        return AdvsID;
    }

    public void setAdvsID(String advsID) {
        AdvsID = advsID;
    }

    public AdvsRVModal(String titleAdvs, String mobileAdvs, String sectionAdvs, String genderAdvs, String job_TypeAdvs, String locationAdvs, String nationalityAdvs, String salaryAdvs, String descriptionAdvs, String advsID) {
        TitleAdvs = titleAdvs;
        MobileAdvs = mobileAdvs;
        SectionAdvs = sectionAdvs;
        GenderAdvs = genderAdvs;
        Job_TypeAdvs = job_TypeAdvs;
        LocationAdvs = locationAdvs;
        NationalityAdvs = nationalityAdvs;
        SalaryAdvs = salaryAdvs;
        DescriptionAdvs = descriptionAdvs;
        AdvsID = advsID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TitleAdvs);
        dest.writeString(MobileAdvs);
        dest.writeString(SectionAdvs);
        dest.writeString(GenderAdvs);
        dest.writeString(Job_TypeAdvs);
        dest.writeString(LocationAdvs);
        dest.writeString(NationalityAdvs);
        dest.writeString(SalaryAdvs);
        dest.writeString(DescriptionAdvs);
        dest.writeString(AdvsID);
    }
}
