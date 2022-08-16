package com.weber.cs3230.adminapp;

public class IntentsAndAnswers {
    public int id = 0;
    public String intent = "";
    public String answer = "";
    public String date = "";
    
    public IntentsAndAnswers(int id, String intent, String date) {
        this.id = id++;
        this.intent = intent;
        this.date = date;
    
    
    }
    
    public IntentsAndAnswers() {

    }
    
    public final IntentsAndAnswers getAnswerObject(){
        return new IntentsAndAnswers(id, answer, date);
    }
    
    public final IntentsAndAnswers getIntentObject() {
        //String idTemp = Integer.toString(id);
        
        return new IntentsAndAnswers(id, intent, date);
    }
    
    
}
