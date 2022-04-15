package com.editorassistant.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.editorassistant.dto.EventDto;
import com.editorassistant.entity.Event;
import com.editorassistant.entity.Paper;
import com.editorassistant.entity.PaperAttachement;
import com.editorassistant.exceptions.EditorAssistantException;
import com.editorassistant.repositories.EventRepository;
import com.editorassistant.repositories.PaperAttachementRepository;
import com.editorassistant.repositories.PaperRepository;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private PaperRepository paperRepository;
	
	@Autowired
	private PaperAttachementRepository paperAttachementRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public boolean createEvent(Event event) {
		//Event event = new Event();
		if(event == null) {
			throw new EditorAssistantException("Event should not be null!");
		}
		
		eventRepository.save(event);
		
		return true;
	}

	@Override
	public boolean updateEvent(Event event) {
		Event dbEvent = event != null ? eventRepository.findById(event.getEventId()).get() : null;
		if(event != null && dbEvent == null) {
			throw new EditorAssistantException("Event not found with Id: "+event.getEventId());
		}
		
		eventRepository.save(event);
		
		return true;
	}

	@Override
	public List<EventDto> getAllRegisteredEvents(BigInteger authorId) {
		
		return modelToDto(eventRepository.findAll(), authorId) ;
	}

	@Override
	public boolean deleteEvent(BigInteger eventId) {
		
		Event event = eventRepository.findById(eventId).get();
		
		List<Paper> eventPaperList = paperRepository.findByEventId(eventId);
		
		List<BigInteger> paperIdList = eventPaperList.stream().map(paper -> paper.getPaperId()).collect(Collectors.toList());
		
		List<PaperAttachement> paperAttachmentList = paperAttachementRepo.findAllById(paperIdList);
		if(event == null) {
			throw new EditorAssistantException("Event not Found with Id: "+eventId);
		}
		  try {
			  paperRepository.deleteAll(eventPaperList);
			  paperAttachementRepo.deleteAll(paperAttachmentList);
			  eventRepository.delete(event);
			 
		} catch (Exception e) {
			throw new EditorAssistantException("Error Occured While Deleting the Event Details: "+e.getMessage());
		}
		
		return true;
	}
	
	private List<EventDto> modelToDto(List<Event> eventList, BigInteger authorId) {
		List<Paper> paperListByAuthor  = paperRepository.findByAuthorId(authorId);
		
		List<EventDto> listEventDto = eventList.stream().map(event -> modelMapper.map(event, EventDto.class)).collect(Collectors.toList());
		
		List<EventDto> eventDtoList = new ArrayList<EventDto>();
		for (EventDto eventDto : listEventDto) {
			for (Paper paper : paperListByAuthor) {
				if(paper.getEventId().equals(eventDto.getEventId())) {
					eventDto.setRegistered(true);
				} 
			}
			eventDtoList.add(eventDto);
		}
	
	return eventDtoList;
	}

	@Override
	public List<EventDto> getAllEvents() {
		
		return eventRepository.findAll().stream().map(event -> modelMapper.map(event, EventDto.class)).collect(Collectors.toList());
	}

}
