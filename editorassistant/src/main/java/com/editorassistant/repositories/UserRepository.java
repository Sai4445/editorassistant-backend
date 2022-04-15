package com.editorassistant.repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.editorassistant.entity.User;

public interface UserRepository extends JpaRepository<User, BigInteger> {
	
	User findByEmail(String email);
	
	List<User> findByRole(String role);

}
