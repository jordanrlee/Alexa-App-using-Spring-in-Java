package com.weber.cs3230.answers;

//import com.weber.cs3230.AlexaIntent;
import com.weber.cs3230.AlexaIntent;
import com.weber.cs3230.MetricsRecorder;
import com.weber.cs3230.NoAvailableAnswerException;
import com.weber.cs3230.data.AlexaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DBAnswerGenerator {
    @Autowired
    public DBAnswerGenerator(AlexaDAO alexaDAO) {
        this.alexaDAO = alexaDAO;
    }
    
    private final AlexaDAO alexaDAO;
    
    protected final static Map<AlexaIntent, String> prevAnswerMap = new HashMap<>();
    
    public final MetricsRecorder metricsRecorder = new MetricsRecorder();
    //@Override
    public String generateText(AlexaIntent intent) throws NoAvailableAnswerException {
        final List<String> possibleAnswers = alexaDAO.getAnswersForIntent(intent.getIntentName());
        // if the intent is invalid, throw an exception
        if (possibleAnswers.isEmpty()) {
            throw new NoAvailableAnswerException(AlexaIntent.getIntentFromString(intent.getIntentName()));
        }
        String tempAnswer = possibleAnswers.get(0);
        String currentAnswer = possibleAnswers.get(0);
        // prevent the same answer from being returned twice in a row
        while (currentAnswer.equals("") || currentAnswer.equals(tempAnswer) || currentAnswer.equals(prevAnswerMap.get(intent))) {
                currentAnswer = possibleAnswers.get((int) (Math.random() * possibleAnswers.size()));
            }
        prevAnswerMap.put(intent, currentAnswer);
        currentAnswer = "This new version is working";
            // send a metric
        metricsRecorder.sendMetric("answer generated");
        return currentAnswer;
        }
}

