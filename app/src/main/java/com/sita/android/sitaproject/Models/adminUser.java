package com.sita.android.sitaproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class adminUser implements Parcelable {

    private  String userName;
    private  String passWord;
    private  String status;

    public adminUser(String userName, String passWord, String status){

        this.userName= userName;
        this.passWord = passWord;
        this.status = status;
    }

    protected adminUser(Parcel in) {
        userName = in.readString();
        passWord = in.readString();
        status = in.readString();
    }

    public static final Creator<adminUser> CREATOR = new Creator<adminUser>() {
        @Override
        public adminUser createFromParcel(Parcel in) {
            return new adminUser(in);
        }

        @Override
        public adminUser[] newArray(int size) {
            return new adminUser[size];
        }
    };

    public String getPassWord() {
        return passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(passWord);
        dest.writeString(status);
    }
}
