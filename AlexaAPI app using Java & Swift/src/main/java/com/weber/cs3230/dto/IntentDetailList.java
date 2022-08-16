package com.weber.cs3230.dto;

import java.util.ArrayList;
import java.util.List;

public class IntentDetailList {

    private List<IntentDetail> intents = new ArrayList<>();

    public List<IntentDetail> getIntents() {
        return intents;
    }

    public void setIntents(List<IntentDetail> intentDetails) {
        this.intents = intentDetails;
    }
}
