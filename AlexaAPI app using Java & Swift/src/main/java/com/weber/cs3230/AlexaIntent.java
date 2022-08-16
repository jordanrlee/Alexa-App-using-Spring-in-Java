package com.weber.cs3230;

import com.weber.cs3230.answers.*;

import java.util.Arrays;

public enum AlexaIntent {
    RICKROLL("rick_roll_bait"),
    BADLUCKBRIAN("bad_luck_brian"),
    ONEDOESNOTSIMPLY("one_does_not_simply"),
    UGANDANKNUCKLES("ugandan_knuckles"),
    TROLLFACE("troll_face"),
    BARRELROLL("barrel_roll"),
    OVERLYATTACHEDGIRLFRIEND("overly_attached_girlfriend"),
    ANCIENTALIENS("ancient_aliens"),
    MONKAS("monkas"),
    FUTURAMAFRY("futurama_fry");
    private final String intentName;
    AlexaIntent(String intentName) {
        this.intentName = intentName;
    }
    
        public static AlexaIntent getIntentFromString(String intentString) {
        return Arrays.stream(AlexaIntent.values())
                .filter(intent -> intent.intentName.equalsIgnoreCase(intentString))
                .findFirst()
                .orElse(null);
    }
    
    public String getIntentName() {
        return intentName;
    }
}
