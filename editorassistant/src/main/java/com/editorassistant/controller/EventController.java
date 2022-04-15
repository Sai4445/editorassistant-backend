package com.editorassistant.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.editorassistant.config.EditorAssistantServiceBase;
import com.editorassistant.dto.EventDto;
import com.editorassistant.entity.Event;
import com.editorassistant.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController extends EditorAssistantServiceBase {
	
	@Autowired
	private EventService eventService;

	@PostMapping("/createEvent")
	public Object createEvent(@Valid @RequestBody Event event, Errors errors) //throws EditorAssistantValidationException {
	{	 
		checkAdmin();
		event.setCreatedBy(getSessionUser().getEmail());
		event.setCreatedDate(LocalDateTime.now());
		return eventService.createEvent(event);
	}
	
	@PutMapping("/updateEvent")
	public Object updateEvent(@Valid @RequestBody Event event, Errors errors) {
		checkAdmin();
		event.setModifiedBy(getSessionUser().getEmail());
		event.setModifiedDate(LocalDateTime.now());
		return eventService.updateEvent(event);
	}
	
	@GetMapping("/getAllEvents")
	public List<EventDto> getAllEvents() {
		checkUser();
		return eventService.getAllEvents();
	}
	
	@GetMapping("/getAllRegisteredEvents/{authorId}")
	public List<EventDto> getAllEvents(@PathVariable BigInteger authorId) {
		checkUser();
		return eventService.getAllRegisteredEvents(authorId);
	}
	
	@DeleteMapping("/deleteEventById/{eventId}") 
	public boolean deleteEvent(@PathVariable BigInteger eventId) {
		checkAdmin();
		return eventService.deleteEvent(eventId);
	}
	
}
