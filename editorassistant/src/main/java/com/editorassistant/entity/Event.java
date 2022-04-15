package com.editorassistant.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "event")
public class Event {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name = "event_id", columnDefinition = "BIGINT")
    private BigInteger eventId;
    
	@NotNull(message = "Event Name is Required")
	@Column(name = "event_name")
    private String eventName;
    
	@Column(name = "description")
    private String description;
    
	@NotNull(message = "Event StartDate should not be Null")
	@Column(name="start_date")
    private LocalDateTime startDateTime;
    
	@NotNull(message = "Event EndDate should not be Null")
	@Column(name = "end_date")
    private LocalDateTime endDateTime;
    
	@NotBlank(message = "createdBy Should not be Blank")
	@Column(name = "creadted_by")
    private String createdBy;
    
	@Column(name = "created_date")
    private LocalDateTime createdDate;
    
	@Column(name = "modified_by")
    private String modifiedBy;
    
	@Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    

}
