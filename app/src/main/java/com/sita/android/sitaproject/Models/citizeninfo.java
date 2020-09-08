package com.sita.android.sitaproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class citizeninfo implements Parcelable {

    String formnumber;
    String firstname;
    String surname;
    String othername;
    String residentiallga;
    String gender;
    String phonenumber;
    String maritalstatus;
    String occupation;
    String picturepath;
    String orin;
    String address;
    String occupationid;
    String lga_id;

    public  citizeninfo(String formnumber, String firstname, String surname, String othername, String residentiallga, String gender, String phonenumber
    ,String maritalstatus, String occupation, String picturepath,String orin,String address){

        this.formnumber = formnumber;
        this.firstname = firstname;
        this.surname = surname;
        this.othername = othername;
        this.residentiallga = residentiallga;
        this.gender = gender;
        this.phonenumber = phonenumber;
        this.maritalstatus = maritalstatus;
        this.occupation = occupation;
        this.picturepath = picturepath;
        this.orin = orin;
        this.address = address;
    }


    protected citizeninfo(Parcel in) {
        formnumber = in.readString();
        firstname = in.readString();
        surname = in.readString();
        othername = in.readString();
        residentiallga = in.readString();
        gender = in.readString();
        phonenumber = in.readString();
        maritalstatus = in.readString();
        occupation = in.readString();
        picturepath = in.readString();
        orin = in.readString();
        address = in.readString();
        occupationid = in.readString();
        lga_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(formnumber);
        dest.writeString(firstname);
        dest.writeString(surname);
        dest.writeString(othername);
        dest.writeString(residentiallga);
        dest.writeString(gender);
        dest.writeString(phonenumber);
        dest.writeString(maritalstatus);
        dest.writeString(occupation);
        dest.writeString(picturepath);
        dest.writeString(orin);
        dest.writeString(address);
        dest.writeString(occupationid);
        dest.writeString(lga_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<citizeninfo> CREATOR = new Creator<citizeninfo>() {
        @Override
        public citizeninfo createFromParcel(Parcel in) {
            return new citizeninfo(in);
        }

        @Override
        public citizeninfo[] newArray(int size) {
            return new citizeninfo[size];
        }
    };

    public String getOthername() {
        return othername;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getFormnumber() {
        return formnumber;
    }

    public String getGender() {
        return gender;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public String getPicturepath() {
        return picturepath;
    }

    public String getResidentiallga() {
        return residentiallga;
    }

    public String getSurname() {
        return surname;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setFormnumber(String formnumber) {
        this.formnumber = formnumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath;
    }

    public void setResidentiallga(String residentiallga) {
        this.residentiallga = residentiallga;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrin() {
        return orin;
    }

    public void setOrin(String orin) {
        this.orin = orin;
    }

    public String getLga_id() {
        return lga_id;
    }

    public String getOccupationid() {
        return occupationid;
    }

    public void setLga_id(String lga_id) {
        this.lga_id = lga_id;
    }

    public void setOccupationid(String occupationid) {
        this.occupationid = occupationid;
    }


}

