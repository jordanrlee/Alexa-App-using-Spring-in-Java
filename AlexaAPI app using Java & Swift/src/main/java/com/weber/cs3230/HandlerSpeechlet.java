package com.weber.cs3230;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.weber.cs3230.dto.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class HandlerSpeechlet implements SpeechletV2 {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private AlexaIntentHandler alexaIntentHandler;
    private final AlexaUtils alexaUtils = new AlexaUtils("Is there any other questions?");
    
    @Autowired
    public AlexaIntentHandler handlerSpeechlet(AlexaIntentHandler alexaIntentHandler) {
        return this.alexaIntentHandler = alexaIntentHandler;
    }
    
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        try {
            log.info("Session started");
        }
        catch (Exception e) {
            log.error("ERROR: session couldn't start..", e);
        }
    }
    
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        try {
            log.info("Session ended");
        }
        catch (Exception e) {
            log.error("ERROR: session couldn't end..", e);
        }
    }
    
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        log.info("onLaunch is starting");
        try {
        log.info("onLaunch is running");
        } catch (Exception e) {
            log.error("caught exception in onLaunch, fixing it..", e);
            return alexaUtils.getOnLaunchResponse(requestEnvelope.getSession(), "Rick Roll Knowledge", "You have entered, the Meme Zone. You can ask me to tell you a meme, or you can ask me to tell you a meme by keyword. What would you like to do?");
        }
        log.info("onLaunch ran with no exception and is returning a response");
        return alexaUtils.getOnLaunchResponse(requestEnvelope.getSession(), "Rick Roll Knowledge", "You have entered, the Meme Zone. You can ask me to tell you a meme, or you can ask me to tell you a meme by keyword. What would you like to do?");
    }
    
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        Answer answer = null;
        try {
            answer = alexaIntentHandler.handleIntent(requestEnvelope.getRequest().getIntent().getName());
        } catch (NoAvailableAnswerException e) {
            log.warn("error found when creating an answer", e);
            throw new RuntimeException(e);
        }
        try {
            if (answer != null) {
                log.info("onIntent is running");
                answer = alexaIntentHandler.handleIntent(requestEnvelope.getRequest().getIntent().getName());
    
            }
        } catch (NoAvailableAnswerException e) {
            log.warn("onIntent created an UNRECOGNIZED(BAD) response");
            return alexaUtils.getUnrecognizedResponse(requestEnvelope.getSession(), "ERROR: Response", "Nothing was found from the request");
        } catch (Exception e) {
            log.error("onIntent created an exception error");
            return alexaUtils.getUnrecognizedResponse(requestEnvelope.getSession(), "ERROR: Response", "Unrecognized response (bad)");
        }
        // if no issue with the answer, return the answer
        return alexaUtils.getNormalResponse(requestEnvelope.getSession(), "Rick Roll Knowledge", "this is the knowledge you asked for concerning the Meme: " + answer.getText());
    }
}
