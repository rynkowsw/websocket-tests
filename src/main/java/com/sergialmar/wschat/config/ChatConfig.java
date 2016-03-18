package com.sergialmar.wschat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.sergialmar.wschat.event.ParticipantRepository;
import com.sergialmar.wschat.event.PresenceEventListener;

@Configuration
public class ChatConfig {

	public static class Destinations {
		private Destinations() {
		}

		private static final String LOGIN = "/topic/login";
		private static final String LOGOUT = "/topic/logout";
	}

	private static final int MAX_PROFANITY_LEVEL = 5;

	@Bean
	@Description("Tracks user presence (join / leave) and broacasts it to all connected users")
	public PresenceEventListener presenceEventListener(SimpMessagingTemplate messagingTemplate) {
		PresenceEventListener presence = new PresenceEventListener(messagingTemplate, participantRepository());
		presence.setLoginDestination(Destinations.LOGIN);
		presence.setLogoutDestination(Destinations.LOGOUT);
		return presence;
	}

	@Bean
	@Description("Keeps connected users")
	public ParticipantRepository participantRepository() {
		return new ParticipantRepository();
	}

}
