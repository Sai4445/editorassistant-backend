package com.editorassistant.service;

import javax.servlet.http.HttpServletRequest;

import com.editorassistant.dto.LoginDto;
import com.editorassistant.dto.LoginResponse;

public interface LoginService {
	
	LoginResponse authenticateUser(HttpServletRequest request, LoginDto loinDto);
	
	boolean  logoutUser(HttpServletRequest request);
	
	 LoginResponse validateSession(HttpServletRequest request);

}
