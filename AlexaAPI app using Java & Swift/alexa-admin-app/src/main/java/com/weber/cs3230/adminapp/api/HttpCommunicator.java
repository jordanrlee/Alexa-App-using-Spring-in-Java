package com.weber.cs3230.adminapp.api;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpCommunicator {

    public <T> T communicate(String method, String urlString, Class<T> responseClass) {
        return communicate(method, urlString, null, responseClass);
    }

    public <T> T communicate(String method, String urlString, String payload, Class<T> responseClass) {
        try {
            final boolean hasPayload = payload != null && payload.trim().length() > 0;
            final URL url = new URL(urlString);
            final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setReadTimeout(20_000); //20 second timeouts
            urlConnection.setConnectTimeout(20_000);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            if (hasPayload) {
                try (OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), StandardCharsets.UTF_8)) {
                    writer.write(payload);
                }
            }

            final int statusCode = urlConnection.getResponseCode();
            final boolean goodResponseCode = statusCode < HttpURLConnection.HTTP_BAD_REQUEST;
            final InputStream inputStream;
            if(goodResponseCode) {
                inputStream = urlConnection.getInputStream();
            }
            else {
                inputStream = urlConnection.getErrorStream();
            }

            final String response = inputStreamToString(inputStream);
            if (!goodResponseCode) {
                throw new RuntimeException("Communication to " + urlString + " failed with status code " + statusCode + " and response " + response);
            }

            try {
                return new Gson().fromJson(response, responseClass);
            } catch (Exception e) {
                if (responseClass == response.getClass()) {
                    System.out.println("Failed to parse response into JSON, returning raw response instead...");
                    return responseClass.cast(response);
                }
                throw e;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to run communication for " + urlString + " " + method, e);
        }
    }

    private String inputStreamToString(final InputStream inputStream) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (inputStream;
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader rd = new BufferedReader(reader)) {
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}