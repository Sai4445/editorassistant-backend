package com.editorassistant.enums;

public enum UserRoleType {
	
	ADMIN("admin"),
	REVIEWER("reviewer"),
	AUTHOR("author");
	
	private String role;

	private UserRoleType(String role) {
		this.role = role;
	}
	
	public String getValue() {
		return this.role;
	}
	

}
