package com.editorassistant.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Entity
@Table(name = "paper")
public class Paper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 124846521888998325L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "paper_id", columnDefinition = "BIGINT")
	private BigInteger paperId;
	
	@NotNull(message = "EventId should not be Null")
	@Column(name = "event_id", columnDefinition = "BIGINT")
	private BigInteger eventId;
	
	@NotNull(message = "AuthorId should not be Null")
	@Column(name = "author_id", columnDefinition = "BIGINT")
	private BigInteger authorId;
	
	@Column(name = "reviewer_1", columnDefinition = "BIGINT")
	private BigInteger reviewer1;
	
	@Column(name = "reviewer_2", columnDefinition = "BIGINT")
	private BigInteger reviewer2;
	
	@Column(name = "reviewer_3", columnDefinition = "BIGINT")
	private BigInteger reviewer3;
	
	@Column(name = "paper_status")
    private String paperStatus;
    
	@Column(name = " reviewer1_score")
    private int reviewer1Score;
    
	@Column(name = " reviewer2_score")
    private int reviewer2Score;
    
	@Column(name = " reviewer3_score")
    private int reviewer3Score;
	
	@Column(name = "reviewer1_feedback")
	private String reviewer1Feedback;
	
	@Column(name = "reviewer2_feedback")
	private String reviewer2Feedback;
	
	@Column(name = "reviewer3_feedback")
	private String reviewer3Feedback;
    
	@Column(name = " average_score")
    private Float averageScore;
	
	@Transient
	private MultipartFile paperAttachement;
	
//	@Column(name = "is_submitted")
//	private boolean isSubmitted;

}
