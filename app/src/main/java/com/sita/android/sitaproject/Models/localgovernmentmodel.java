package com.sita.android.sitaproject.Models;

public class localgovernmentmodel {

    private String localGovernmentName;
    private String id;

   public localgovernmentmodel(String localGovernmentName, String id){
        this.localGovernmentName = localGovernmentName;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getLocalGovernmentName() {
        return localGovernmentName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocalGovernmentName(String localGovernmentName) {
        this.localGovernmentName = localGovernmentName;
    }
}
