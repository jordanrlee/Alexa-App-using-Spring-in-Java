package com.weber.cs3230.dto;

import java.sql.Timestamp;

public class Metric {
    private long metricID;
    private String appName;
    private String eventName;
    private Timestamp dtStamp;

    public long getMetricID() {
        return metricID;
    }

    public void setMetricID(long metricID) {
        this.metricID = metricID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Timestamp getDtStamp() {
        return dtStamp;
    }

    public void setDtStamp(Timestamp dtStamp) {
        this.dtStamp = dtStamp;
    }
}
