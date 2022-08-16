package com.weber.cs3230;

import com.weber.cs3230.answers.DBAnswerGenerator;
import com.weber.cs3230.dto.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class AlexaIntentHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private final DBAnswerGenerator dbAnswerGenerator;
    
    @Autowired
    public AlexaIntentHandler(DBAnswerGenerator dbAnswerGenerator) {
        this.dbAnswerGenerator = dbAnswerGenerator;
    }
    
    public Answer handleIntent(@PathVariable String intentString) throws NoAvailableAnswerException {
        
            AlexaIntent intent = AlexaIntent.getIntentFromString(intentString);
            if (intent == null) {
                log.info("intent was very null my friend, so this won't work.");
                return null;
            }
            //answerGenerator = intent.getTextGenerator();
            log.info("intent received: " + intent);
            return new Answer(dbAnswerGenerator.generateText(intent));
    }
}
