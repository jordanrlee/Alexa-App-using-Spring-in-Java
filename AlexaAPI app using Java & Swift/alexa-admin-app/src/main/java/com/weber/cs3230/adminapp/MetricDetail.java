package com.weber.cs3230.adminapp;

public class MetricDetail {
    private long count;
    private String eventName;
    private String mostRecentDate;
    
    public long getCount() {
        return count;
    }
    
    public void setCount(long count) {
        this.count = count;
    }
    
    public String getEventName() {
        return eventName;
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    public String getMostRecentDate() {
        return mostRecentDate;
    }
    
    public void setMostRecentDate(String mostRecentDate) {
        this.mostRecentDate = mostRecentDate;
    }
}
