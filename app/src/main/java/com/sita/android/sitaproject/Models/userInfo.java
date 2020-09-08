package com.sita.android.sitaproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class userInfo implements Parcelable {

    private String id;
    private String pinnumber;
    private String firstname;
    private String lastname;
    private String othername;
    private String Gender;
    private String localGovernment;
    private String maritalstatus;
    private String profilepictuerpath;


   public userInfo(String id, String pinnumber, String firstname, String lastname, String othername, String Gender,String localGovernment,
              String maritalstatus, String profilepictuerpath){
        this.id=id;
        this.firstname=firstname;
        this.lastname=lastname;
        this.othername=othername;
        this.Gender = Gender;
        this.localGovernment = localGovernment;
        this.maritalstatus = maritalstatus;
        this.profilepictuerpath = profilepictuerpath;
    }

    protected userInfo(Parcel in) {
        id = in.readString();
        pinnumber = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        othername = in.readString();
        Gender = in.readString();
        localGovernment = in.readString();
        maritalstatus = in.readString();
        profilepictuerpath = in.readString();
    }

    public static final Creator<userInfo> CREATOR = new Creator<userInfo>() {
        @Override
        public userInfo createFromParcel(Parcel in) {
            return new userInfo(in);
        }

        @Override
        public userInfo[] newArray(int size) {
            return new userInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pinnumber);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(othername);
        dest.writeString(Gender);
        dest.writeString(localGovernment);
        dest.writeString(maritalstatus);
        dest.writeString(profilepictuerpath);
    }
}
