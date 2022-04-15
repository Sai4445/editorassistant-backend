package com.editorassistant.dto;

import lombok.Data;

@Data
public class ResponseSuccessDto {

	private String status;
	
	private String message;
	
	private Object data;
	
	public ResponseSuccessDto(Object data) {
		this.data = data;
		this.status = "SUCCESS";
	}
}
