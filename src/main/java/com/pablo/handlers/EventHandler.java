package com.pablo.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.pablo.domain.Administrative;

/**
 * 
 * @author Pablo
 *
 * This is a pub-sub approach so that one message is relayed to every attached consumer. The route of
 * each message is different, allowing multiple messages to be sent to distinct receivers on the client
 * while needing only one open WebSocket — a resource-efficient approach.
 * 
 * 
 */
@Component
@RepositoryEventHandler(Administrative.class) // flags this class to trap events based on Administratives
public class EventHandler {

	private static final String MESSAGE_PREFIX = "/topic";
	private final SimpMessagingTemplate websocket;
	private final EntityLinks entityLinks;

	@Autowired
	public EventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}

	@HandleAfterCreate
	public void newAdministrative(Administrative administrative) {
		this.websocket.convertAndSend(MESSAGE_PREFIX + "/newAdministrative",
				getPath(administrative));
	}

	@HandleAfterDelete
	public void deleteAdministrative(Administrative administrative) {
		this.websocket.convertAndSend(MESSAGE_PREFIX + "/deleteAdministrative",
				getPath(administrative));
	}

	@HandleAfterSave
	public void updateAdministrative(Administrative administrative) {
		this.websocket.convertAndSend(MESSAGE_PREFIX + "/updateAdministrative",
				getPath(administrative));
	}

	/**
	 * Take an {@link Administrative} and get the URI using Spring Data REST's {@link EntityLinks}.
	 *
	 * @param administrative
	 */
	private String getPath(Administrative administrative) {
		return this.entityLinks
				.linkForItemResource(administrative.getClass(), administrative.getId())
				.toUri().getPath();
	}

}
