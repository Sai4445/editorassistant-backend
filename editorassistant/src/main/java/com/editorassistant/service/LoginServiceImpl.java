package com.editorassistant.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.editorassistant.config.EditorAssistantConstants;
import com.editorassistant.dto.LoginDto;
import com.editorassistant.dto.LoginResponse;
import com.editorassistant.entity.User;
import com.editorassistant.exceptions.EditorAssistantException;
import com.editorassistant.repositories.UserRepository;
import com.editorassistant.utils.AppUtils;


@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AppUtils appUtils;

	@Override
	public LoginResponse authenticateUser(HttpServletRequest request, LoginDto loginDto) {
		
		LoginResponse response = null;
		
		if(loginDto != null ) {
			User user = loginDto.getEmail() != null ? userRepo.findByEmail(loginDto.getEmail()) : null;
			
			boolean isPasswordMatches = false;
			boolean isEmailExists = user != null ? true : false;
			if(isEmailExists) {
				isPasswordMatches = isEmailExists ? appUtils.passwordEncoder().matches(loginDto.getPassword(), user.getPassword()) : false;
			} else {
				throw new EditorAssistantException("User [ "+loginDto.getEmail()+" ] not Registered!!");
			}
			
			if(isEmailExists && isPasswordMatches) {
				response = new LoginResponse(user, true);
				
				try {
					request.getSession().invalidate();
				} catch (Exception e) {
					System.out.println("in catch 51");
					e.printStackTrace();
				}
				System.out.println(" User Logged in Successfully: "+user.toString());
				request.getSession().setAttribute(EditorAssistantConstants.SESSION_USER_KEY, user);
				request.getSession().setAttribute(EditorAssistantConstants.SESSION_KEY_USERRANDOM, response.getRandom());
				System.out.println("after cache 57"+request.getSession().getId());
			} else {
				throw new EditorAssistantException("Invalid Email/Password!!");
			}
		}
		return response;
	}
	
    public boolean logoutUser(HttpServletRequest request) {
		
		try {
			request.getSession().invalidate();
		} catch (Exception e) {
			
		}
		return true;
		
	}
    
    public LoginResponse validateSession(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(EditorAssistantConstants.SESSION_USER_KEY);
		if(user != null && user.getUserId() != null) {
			Optional<User> dbUser = userRepo.findById(user.getUserId());
			String random = (String) request.getSession().getAttribute(EditorAssistantConstants.SESSION_KEY_USERRANDOM);
			
			if( dbUser.isPresent() && random != null ) {
				LoginResponse response = new LoginResponse(dbUser.get());
				response.setRandom(random);
			    
			    return response;
			}
		}
		
		 throw new EditorAssistantException(EditorAssistantConstants.SESSION_ERROR_RESP_USERNOTAUTH);
	}
}
