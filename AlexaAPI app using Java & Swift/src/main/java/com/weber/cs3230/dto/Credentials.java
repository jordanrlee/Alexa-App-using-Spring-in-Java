package com.weber.cs3230.dto;

import com.weber.cs3230.MetricsRecorder;

public class Credentials {
    MetricsRecorder metricsRecorder = new MetricsRecorder();
    private String username;
    private String password;

    public String getUsername() {
        metricsRecorder.sendMetric("Username obtained for cred check");
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        metricsRecorder.sendMetric("Password obtained for cred check");
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
