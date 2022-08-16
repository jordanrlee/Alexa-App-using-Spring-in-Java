package com.weber.cs3230;

public class NoAvailableAnswerException extends Exception {
    private final AlexaIntent alexaIntent;
    public NoAvailableAnswerException(AlexaIntent alexaIntent) {
        this.alexaIntent = alexaIntent;
    }
    public AlexaIntent getAlexaIntent() {
        return alexaIntent;
    }
}
