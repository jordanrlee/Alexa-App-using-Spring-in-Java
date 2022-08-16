package com.weber.cs3230.controller;

import com.weber.cs3230.data.AlexaDAO;
import com.weber.cs3230.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AlexaDAO alexaDAO;

    @Autowired
    public MainRestController(AlexaDAO alexaDAO) {
        this.alexaDAO = alexaDAO;
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String healthCheck() {
        log.info("health check request");
        return "up and running";
    }

    @RequestMapping(value = "/credentials", method = RequestMethod.POST)
    public ResponseEntity<String> validateCreds(@RequestBody Credentials credentials) {
        final boolean validCreds = alexaDAO.validateCreds(credentials);
        if (validCreds) {
            return new ResponseEntity<>("\"valid creds\"", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("invalid creds", HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/intents", method = RequestMethod.GET)
    public IntentDetailList getIntents() {
        return alexaDAO.getIntentList();
    }

    @RequestMapping(value = "/intent", method = RequestMethod.POST)
    public IntentDetail saveNewIntent(@RequestBody IntentDetail intent) {
        return alexaDAO.saveNewIntent(intent);
    }

    @RequestMapping(value = "/intent/{intentID}", method = RequestMethod.PUT)
    public IntentDetail updateIntent(@PathVariable long intentID, @RequestBody IntentDetail intent) {
        intent.setIntentID(intentID);
        return alexaDAO.saveExistingIntent(intent);
    }

    @RequestMapping(value = "/intent/{intentID}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteIntent(@PathVariable long intentID) {
        alexaDAO.deleteIntent(intentID);
        return new ResponseEntity<>("deleted intent " + intentID, HttpStatus.OK);
    }

    @RequestMapping(value = "/intent/{intentID}/answers", method = RequestMethod.GET)
    public IntentAnswerList getAnswers(@PathVariable long intentID) {
        return alexaDAO.getAnswerList(intentID);
    }

    @RequestMapping(value = "/intent/{intentID}/answer", method = RequestMethod.POST)
    public IntentAnswer saveNewAnswer(@PathVariable long intentID, @RequestBody IntentAnswer answer) {
        return alexaDAO.saveNewAnswer(answer, intentID);
    }

    @RequestMapping(value = "/intent/{intentID}/answer/{answerID}", method = RequestMethod.PUT)
    public IntentAnswer updateAnswer(@PathVariable long intentID, @PathVariable long answerID, @RequestBody IntentAnswer answer) {
        answer.setAnswerID(answerID);
        return alexaDAO.saveExistingAnswer(answer, intentID);
    }

    @RequestMapping(value = "/intent/{intentID}/answer/{answerID}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAnswer(@PathVariable long intentID, @PathVariable long answerID) {
        alexaDAO.deleteAnswer(intentID, answerID);
        return new ResponseEntity<>("deleted answer " + answerID, HttpStatus.OK);
    }
    @RequestMapping(value = "/metrics", method = RequestMethod.GET)
    public MetricDetailList getMetrics() {
        return alexaDAO.getMetricList();
    }
}
