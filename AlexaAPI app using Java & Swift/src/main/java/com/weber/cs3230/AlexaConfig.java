package com.weber.cs3230;

import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import com.weber.cs3230.HandlerSpeechlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AlexaConfig {

	private final HandlerSpeechlet handlerSpeechlet;

	@Autowired
	public AlexaConfig(HandlerSpeechlet handlerSpeechlet) {
		this.handlerSpeechlet = handlerSpeechlet;
	}

	@Bean
	public ServletRegistrationBean<SpeechletServlet> registerSpeechletServlet() {
		SpeechletServlet speechletServlet = new SpeechletServlet();
		
		speechletServlet.setSpeechlet(handlerSpeechlet);
		return new ServletRegistrationBean<>(speechletServlet, "/alexa");
	}

}
