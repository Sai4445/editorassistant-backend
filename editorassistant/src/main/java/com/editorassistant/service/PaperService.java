package com.editorassistant.service;

import java.math.BigInteger;
import java.util.List;

import com.editorassistant.dto.FieldDto;
import com.editorassistant.entity.Paper;

public interface PaperService {
	
	boolean addAuthorPaper(Paper paper);
	
	boolean updateAuthorPaper(Paper paper);
	
	List<Paper> getAllPaperByEvent(BigInteger eventId);
	
	List<Paper> getAllPaperByAuthor(BigInteger authorId);
	
	boolean deleteAuthorPaper(BigInteger paperId);
	
	boolean updatePaperStatus(BigInteger paperId, String status);
	
	Paper getPaperById(BigInteger paperId);
	
	byte[] getAtachement(BigInteger paperId);
	
	List<Paper> getAllPapersByReviewerAndEvent(BigInteger reviewerId, BigInteger eventId);
	
    void updatePaperByField(BigInteger paperId, List<FieldDto> fieldDtoList);
    
    List<Paper> getAllPapersByStatusAndEvent(BigInteger eventId, String paperStatus);
}
