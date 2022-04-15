package com.editorassistant.config;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.editorassistant.entity.User;
import com.editorassistant.enums.UserRoleType;
import com.editorassistant.exceptions.EditorAssistantException;

public class EditorAssistantServiceBase {

	@Autowired
	private HttpSession session;
	
	protected User getSessionUser() {
		User user = (User) session.getAttribute(EditorAssistantConstants.SESSION_USER_KEY);
		System.out.println("user ");
		if( user == null) {
			throw new EditorAssistantException("User Not Logged in !!");
		}
		
		return user;
	}
	
	
	protected void setSessionUser(User user) {
		session.setAttribute(EditorAssistantConstants.SESSION_USER_KEY, user);
	}
	
	protected String getUserRole() {
		return getSessionUser().getRole();
	}
	
	protected void checkAdmin() {
		System.out.println(" User role: "+getUserRole());
		if(!StringUtils.equals(getUserRole(), UserRoleType.ADMIN.getValue())) {
			throw new EditorAssistantException("User Not Authorized to Perform the Operation");
		}
	}
	
	protected void checkReviewer() {
		System.out.println(" User role: "+getUserRole());
		if(!StringUtils.equals(getUserRole(), UserRoleType.ADMIN.getValue()) && !StringUtils.equals(getUserRole(), UserRoleType.REVIEWER.getValue())) {
			throw new EditorAssistantException("User Not Authorized to Perform the Operation");
		}
	}
	
    protected void checkUser() {
    	System.out.println(" User role: "+getUserRole());
	if(!StringUtils.equals(getUserRole(), UserRoleType.ADMIN.getValue()) 
			&& !StringUtils.equals(getUserRole(), UserRoleType.REVIEWER.getValue())
			&& !StringUtils.equals(getUserRole(), UserRoleType.AUTHOR.getValue())) {
		throw new EditorAssistantException("User Not Authorized to Perform the Operation");
	}
	}
}
