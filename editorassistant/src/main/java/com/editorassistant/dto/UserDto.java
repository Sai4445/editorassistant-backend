package com.editorassistant.dto;

import java.math.BigInteger;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {

	private BigInteger userId;
	
	@NotBlank(message = "Email Should not be Blank")
	@Email
	private String email;
	
	@NotBlank(message = "First Name Should not be Blank")
	@Size(message = "First Name Minimun Characters Should be greater than 2", min = 2)
	private String firstName;
	
	@NotBlank(message = "Last Name Should not be Blank")
	@Size(message = "Last Name Minimun Characters Should be greater than 2", min = 2)
	private String lastName;
	
	@NotBlank(message = "Password Should not be Blank")
	@Size(message = "Password should be Greater than 6 Characters" , min = 6)
	private String password;
	
	private String role;
}
