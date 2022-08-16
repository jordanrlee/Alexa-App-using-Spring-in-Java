package com.weber.cs3230;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.StandardCard;

public class AlexaUtils {

	private final String SESSION_CONVERSATION_FLAG = "conversation";
	private final String repromptText;

	public AlexaUtils(String repromptText) {
		this.repromptText = repromptText;
	}

	public SpeechletResponse getOnLaunchResponse(Session session, String title, String text) {
		setConversationMode(session);
		return getSpeechletResponse(session, title, text, false);
	}

	public SpeechletResponse getUnrecognizedResponse(Session session, String title, String text) {
		setConversationMode(session);
		return getSpeechletResponse(session, title, text, false);
	}

	public SpeechletResponse getNormalResponse(Session session, String title, String text) {
		return getSpeechletResponse(session, title, text, true);
	}

	private SpeechletResponse getSpeechletResponse(Session session, String title, String text, boolean shouldEndSession) {
		setConversationMode(session);
		Card card = newCard(title, text);
		PlainTextOutputSpeech speech = newSpeech(text);
		return newSpeechletResponse(card, speech, session, shouldEndSession);
	}

	private Card newCard(String cardTitle, String cardText) {
		StandardCard card = new StandardCard();
		card.setTitle(cardTitle);
		card.setText(cardText);
		return card;
	}
	
	private PlainTextOutputSpeech newSpeech(String speechText) {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);
		return speech;
	}
	
	private SpeechletResponse newSpeechletResponse(Card card, PlainTextOutputSpeech speech, Session session, boolean shouldEndSession)  {
		if (inConversationMode(session) && !shouldEndSession) {
			PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
			repromptSpeech.setText(repromptText);
			
			Reprompt reprompt = new Reprompt();
			reprompt.setOutputSpeech(repromptSpeech);
			
			return SpeechletResponse.newAskResponse(speech, reprompt, card);
		} else {
			return SpeechletResponse.newTellResponse(speech, card);
		}
	}

	private void setConversationMode(Session session) {
		session.setAttribute(SESSION_CONVERSATION_FLAG, "true");
	}

	private boolean inConversationMode(Session session) {
		 return session.getAttribute(SESSION_CONVERSATION_FLAG) != null;
	}

}
