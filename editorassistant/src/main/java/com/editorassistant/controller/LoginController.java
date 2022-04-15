package com.editorassistant.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.editorassistant.dto.LoginDto;
import com.editorassistant.dto.LoginResponse;
import com.editorassistant.service.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public LoginResponse login(HttpServletRequest request, @RequestBody LoginDto loginDto) { 
		return loginService.authenticateUser(request, loginDto);
		}
	@GetMapping("/logout")
	public boolean logout(HttpServletRequest request) {
		System.out.println("loggin out!");
		return loginService.logoutUser(request);
	}
	
	@GetMapping("/validateSession")
	public LoginResponse validateSession(HttpServletRequest request) {
		
		return loginService.validateSession(request);
		
	}
	

}
