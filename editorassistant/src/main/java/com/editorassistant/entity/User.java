package com.editorassistant.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId", columnDefinition = "BIGINT", nullable = false, unique = true)
	private BigInteger userId;
	
	@NotBlank(message = "Email Should not be Blank")
	@Email
	@Column(name = "email")
	private String email;
	
	@NotBlank(message = "First Name Should not be Blank")
	@Size(message = "First Name Minimun Characters Should be greater than 2", min = 2)
	@Column(name = "first_name")
	private String firstName;
	
	@NotBlank(message = "Last Name Should not be Blank")
	@Size(message = "Last Name Minimun Characters Should be greater than 2", min = 2)
	@Column(name = "last_name")
	private String lastName;
	
	@NotBlank(message = "Password Should not be Blank")
	@Size(message = "Password should be Greater than 6 Characters" , min = 6)
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private String role;

}
