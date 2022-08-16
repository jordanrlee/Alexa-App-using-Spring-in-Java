package com.weber.cs3230;

import com.google.gson.Gson;
import com.weber.cs3230.dto.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.http.HttpMethod.POST;

public class MetricsRecorder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final HttpCommunicator httpCommunicator = new HttpCommunicator();
    private final ExecutorService executorsService = Executors.newCachedThreadPool();
    public void sendMetric(String eventName) {
        executorsService.submit(() -> {
            String json = null;
            try {
                // create an instance of method object
                Metric metric = new Metric();
                metric.setAppName("jordans_happy_little_metrics");
                metric.setEventName(eventName);
                json = new Gson().toJson(metric);
                log.info("MetricID gathered: " + metric.getMetricID());
        
            } catch (Exception e) {
                log.error("ERROR: call to HttpCommunicator failed", e);
            }
            httpCommunicator.communicate(POST, "https://alexa-ghost.herokuapp.com/metric", json, Metric.class);
        });

    }

}
