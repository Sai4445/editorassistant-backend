package com.editorassistant.advisor;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.editorassistant.dto.ResponseErrorDto;
import com.editorassistant.exceptions.EditorAssistantException;

@ControllerAdvice
public class ApplicationExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		
		List<String> details = new ArrayList<>();
		for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
			details.add(error.getMessage());
		}
		ResponseErrorDto error = new ResponseErrorDto(details);
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(EditorAssistantException.class)
	public ResponseEntity<ResponseErrorDto> exception(EditorAssistantException exception) {
		
		ResponseErrorDto errorResponse = new ResponseErrorDto(exception.getMessage());

		return new ResponseEntity<ResponseErrorDto>(errorResponse, HttpStatus.OK);
	}

	
	  @ExceptionHandler(value = Exception.class) 
	  public ResponseEntity<ResponseErrorDto> five100Exception(EditorAssistantException
	  exception) {
	  
	  ResponseErrorDto errorResponse = new
	  ResponseErrorDto(exception.getMessage());
	  
	  return new ResponseEntity<ResponseErrorDto>(errorResponse, HttpStatus.OK); }
	  
	 
}
