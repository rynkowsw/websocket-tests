package com.sergialmar.wschat.web;

import com.sergialmar.wschat.domain.ChatMessage;
import com.sergialmar.wschat.event.LoginEvent;
import com.sergialmar.wschat.event.ParticipantRepository;
import com.sergialmar.wschat.exception.TooMuchProfanityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Collection;

/**
 * Controller that handles WebSocket chat messages
 * 
 * @author Sergi Almar
 */
@Controller
public class ChatController {
	
	@Autowired private ParticipantRepository participantRepository;
	
	@Autowired private SimpMessagingTemplate simpMessagingTemplate;
	
	@Description("Umożliwia prosty zwrot informacji dla wszystkich subsriberów")
	@SubscribeMapping("/participants")
	public Collection<LoginEvent> retrieveParticipants() {
		return participantRepository.getActiveSessions().values();
	}


	@Description("Wiadomośc dla wszystkich")
	@MessageMapping("/message")
	public ChatMessage filterMessage(@Payload ChatMessage message, Principal principal) {
		message.setUsername(principal.getName());
		return message;
	}

	@Description("Wiadomość dla pojedynczego użytkownika")
	@MessageMapping("/private.{username}")
	public void filterPrivateMessage(@Payload ChatMessage message, @DestinationVariable("username") String username, Principal principal) {
		message.setUsername(principal.getName());
		simpMessagingTemplate.convertAndSend("/user/" + username + "/exchange/message", message);
	}

	
	@MessageExceptionHandler
	@SendToUser(value = "/exchange/errors", broadcast = false)
	public String handleProfanity(TooMuchProfanityException e) {
		return e.getMessage();
	}
}