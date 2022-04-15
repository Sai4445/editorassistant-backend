package com.editorassistant.dto;

import lombok.Data;

@Data
public class ResponseErrorDto {

	private String status;
	
	private String message;
	
	private Object data;
	
	public ResponseErrorDto(Object data) {
		this.status = "FAIL";
		this.data = data;
	}
}
