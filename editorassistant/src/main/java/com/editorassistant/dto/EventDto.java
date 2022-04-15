package com.editorassistant.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventDto {

	private BigInteger eventId;
	
	private String eventName;
	
	private String description;
	
	private LocalDateTime startDateTime;
	
	private LocalDateTime endDateTime;
	
	private String createdBy;
	
	private LocalDateTime createdDate;
	
	private String modifiedBy;
	
	private LocalDateTime modifiedDate;
	
	private boolean isRegistered;
}
