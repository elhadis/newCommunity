package com.rgbat.mycommunity;

public class CtegoryModel {
    private String id,name,noOfSets;

    public CtegoryModel() {
    }

    public CtegoryModel(String id, String name, String noOfSets) {
        this.id = id;
        this.name = name;
        this.noOfSets = noOfSets;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoOfSets() {
        return noOfSets;
    }

    public void setNoOfSets(String noOfSets) {
        this.noOfSets = noOfSets;
    }
}
