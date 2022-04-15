package com.editorassistant.advisor;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.editorassistant.annotations.ExcludeCustomResponseProcessing;
import com.editorassistant.dto.ResponseSuccessDto;


@ControllerAdvice
public class ApplicationResponseAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter type, Class<? extends HttpMessageConverter<?>> messageConverterType) {
		
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object data, MethodParameter type, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> converterType, ServerHttpRequest httpRequest,
			ServerHttpResponse httpResponse) {
		if(!type.getContainingClass().isAnnotationPresent(RestController.class)) {
			
			return data;
		}
		  if(type.getMethod().isAnnotationPresent(
		  ExcludeCustomResponseProcessing.class)) {  return data; }
		  
		  
		ResponseSuccessDto responseSuccess = new ResponseSuccessDto(data);
		
		return responseSuccess;
	}

}
