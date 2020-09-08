package com.sita.android.sitaproject.Models;

public class occupations {

    String occupation;
    String id;

   public occupations(String occupation, String id){

        this.occupation = occupation;
        this.id = id;
    }
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
