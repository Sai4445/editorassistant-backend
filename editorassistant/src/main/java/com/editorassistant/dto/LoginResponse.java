package com.editorassistant.dto;

import java.math.BigInteger;

import org.apache.commons.lang3.RandomStringUtils;

import com.editorassistant.entity.User;

import lombok.Data;

@Data
public class LoginResponse {
	
private BigInteger userId;
	
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String role;
	
	private String random;

	public LoginResponse() {
		
	}
	
	public LoginResponse(User u) {
		
	}
	public LoginResponse(User u, boolean setRandom) {
		addUser(u);
		this.random = setRandom ? RandomStringUtils.random(50, true, true) : null;
  	}
	 private void addUser(User user) {
		   this.userId = user.getUserId();
		   this.email = user.getEmail();
		   this.firstName = user.getFirstName();
		   this.lastName = user.getLastName();
		   this.role = user.getRole();
		   
	   }

}
