package com.weber.cs3230.dto;

import com.weber.cs3230.MetricsRecorder;

import java.util.ArrayList;
import java.util.List;

public class MetricDetailList {
    
    MetricsRecorder metricsRecorder = new MetricsRecorder();
    private final List<MetricDetail> metrics = new ArrayList<>();
    
    public List<MetricDetail> getMetrics() {
        metricsRecorder.sendMetric("getMetrics called from MetricDetailList");
        return metrics;
    }
}
