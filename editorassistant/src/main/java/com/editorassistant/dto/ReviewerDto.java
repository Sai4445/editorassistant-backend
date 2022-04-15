package com.editorassistant.dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class ReviewerDto {
	
	private BigInteger reviewerId;
	
	private String reviewerName;

}
