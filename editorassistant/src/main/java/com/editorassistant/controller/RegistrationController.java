package com.editorassistant.controller;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.editorassistant.config.EditorAssistantServiceBase;
import com.editorassistant.dto.ReviewerDto;
import com.editorassistant.dto.UserDto;
import com.editorassistant.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class RegistrationController extends EditorAssistantServiceBase {

	@Autowired
	private UserServiceImpl userService;
	
	@PostMapping("/registerUser")
	public UserDto registerUser(@Valid @RequestBody UserDto userDto) {
		return userService.registerUser(userDto);
	}
	
	@PutMapping("/updateUser")
	public UserDto updateUser(@Valid @RequestBody UserDto userDto) {
		checkUser();
		return userService.registerUser(userDto);
	}
	
	@GetMapping("/getAllUsers")
	public List<UserDto> getAllUsers() {
		checkUser();
		return userService.getAllUsers();
	}
	
	@GetMapping("/getUserById/{userId}")
	public UserDto getUserById(@PathVariable BigInteger userId) {
		return userService.getUserById(userId);
	}
	
	@DeleteMapping("/deleteUserById/{userId}")
	public boolean deleteUserById(@PathVariable BigInteger userId) {
		return userService.deleteUserById(userId);
	}
	
	@GetMapping("/getAllReviewers")
	public List<ReviewerDto> getAllReviewers() {
		checkUser();
		return userService.getReviewers();
	}
	
	
}
