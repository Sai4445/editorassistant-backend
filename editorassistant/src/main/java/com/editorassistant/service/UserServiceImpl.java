package com.editorassistant.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.editorassistant.dto.ReviewerDto;
import com.editorassistant.dto.UserDto;
import com.editorassistant.entity.User;
import com.editorassistant.exceptions.EditorAssistantException;
import com.editorassistant.repositories.UserRepository;
import com.editorassistant.utils.AppUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modalMapper;
	
	@Autowired
	private AppUtils appUtils;
	
	

	@Override
	public UserDto registerUser(UserDto userDto) {
		
		User u = userRepository.findByEmail(userDto.getEmail());
		if( u != null ) {
			throw new EditorAssistantException("User With Email Already Exists !!");
		}
		
		User user = new User();
		
		if(userDto != null) {
			/*user.setEmail(userDto.getEmail());
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setPassword(userDto.getPassword());
			user.setRole(userDto.getRole());*/
			user = modalMapper.map(userDto, User.class);
			user.setPassword(appUtils.passwordEncoder().encode(userDto.getPassword()));
		}
		 userRepository.save(user);
		return null;
	}

	@Override
	public boolean updateUser(UserDto userDto) {
		
		User user = null;
		
	if(userDto != null && userDto.getUserId() != null) {
		user = userRepository.findById(userDto.getUserId()).get();
	}
	
	if(user == null ) {
		throw new EditorAssistantException("User with "+userDto.getUserId()+" not found ") ;
	}
	
	   user.setEmail(userDto.getEmail());
	   user.setFirstName(userDto.getFirstName());
	   user.setLastName(userDto.getLastName());
	   user.setRole(userDto.getRole());
	   
		return  true;
	}

	@Override
	public UserDto getUserById(BigInteger userId) {
		
		User user = userId != null ?  userRepository.findById(userId).get() : null;
		
		if(user == null) throw new EditorAssistantException("User With: "+userId+" not found");
		
		return modalMapper.map(user, UserDto.class);
	}
	

	@Override
	public boolean deleteUserById(BigInteger userId) {
		
		User user = userId != null ? userRepository.findById(userId).get() : null;
		if(user == null) throw new EditorAssistantException("User With: "+userId+" not found");
		userRepository.delete(user);
		return true;
	}

	@Override
	public List<ReviewerDto> getReviewers() {
		
		List<User> users = userRepository.findByRole("reviewer");
		List<ReviewerDto> riDtos = new ArrayList<ReviewerDto>();
		users.forEach(user -> {
			final ReviewerDto reviewerDto = new ReviewerDto();
			reviewerDto.setReviewerId(user.getUserId());
			reviewerDto.setReviewerName(user.getFirstName()+" "+user.getLastName());
			riDtos.add(reviewerDto);
		});
		
		return riDtos;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		 userRepository.findAll().stream().forEach(user -> {
			 user.setPassword(null);
			 userList.add(user);
		 });;
		
		return userList != null ? userList.stream().map(user ->  modalMapper.map(user, UserDto.class)).collect(Collectors.toList())
				: null; 
	}
	
	
	 
}
