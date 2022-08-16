package com.weber.cs3230.dto;

import com.weber.cs3230.MetricsRecorder;

public class MetricDetail {
    
    MetricsRecorder metricsRecorder = new MetricsRecorder();
    private long count;
    private String eventName;
    private String mostRecentDate;
    
    public long getCount() {
        metricsRecorder.sendMetric("count set from MetricDetail");
    
        return count;
    }
    
    public void setCount(long count) {
        this.count = count;
        metricsRecorder.sendMetric("count obtained from MetricDetail");
    }
    
    public String getEventName() {
        return eventName;
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
        metricsRecorder.sendMetric("eventName obtained from MetricDetail");
    
    }
    
    public String getMostRecentDate() {
        return mostRecentDate;
    }
    
    public void setMostRecentDate(String mostRecentDate) {
        this.mostRecentDate = mostRecentDate;
        metricsRecorder.sendMetric("mostRecentDate obtained from MetricDetail");
    
    }
}
