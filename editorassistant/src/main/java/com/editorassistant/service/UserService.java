package com.editorassistant.service;

import java.math.BigInteger;
import java.util.List;

import com.editorassistant.dto.ReviewerDto;
import com.editorassistant.dto.UserDto;


public interface UserService {
	
	UserDto registerUser(UserDto userDto);
	
	boolean updateUser(UserDto userDto);
	
	UserDto getUserById(BigInteger userId);
	
	boolean deleteUserById(BigInteger userId);
	
	List<ReviewerDto> getReviewers();
	
	List<UserDto> getAllUsers();

}
