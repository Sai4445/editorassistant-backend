package com.editorassistant.service;

import java.math.BigInteger;
import java.util.List;

import com.editorassistant.dto.EventDto;
import com.editorassistant.entity.Event;

public interface EventService {
   
	   boolean createEvent(Event event);
	   
	   boolean updateEvent(Event event);
	   
	   List<EventDto> getAllEvents();
	   
	   boolean deleteEvent(BigInteger eventId);
	   
	   List<EventDto> getAllRegisteredEvents(BigInteger authorId);
}
