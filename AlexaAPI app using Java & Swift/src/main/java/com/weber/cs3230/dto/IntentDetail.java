package com.weber.cs3230.dto;

public class IntentDetail {

    private long intentID;
    private String name;
    private String dateAdded;

    public long getIntentID() {
        return intentID;
    }

    public void setIntentID(long intentID) {
        this.intentID = intentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
