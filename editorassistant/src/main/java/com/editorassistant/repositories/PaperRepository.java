package com.editorassistant.repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.editorassistant.entity.Paper;

public interface PaperRepository extends JpaRepository<Paper, BigInteger> {

	List<Paper> findByEventId(BigInteger eventId); 
	
	List<Paper> findByAuthorId(BigInteger authorId);
}
