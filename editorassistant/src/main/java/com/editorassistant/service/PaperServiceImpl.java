package com.editorassistant.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.editorassistant.config.EditorAssistantConstants;
import com.editorassistant.dto.FieldDto;
import com.editorassistant.entity.Paper;
import com.editorassistant.entity.PaperAttachement;
import com.editorassistant.exceptions.EditorAssistantException;
import com.editorassistant.repositories.PaperAttachementRepository;
import com.editorassistant.repositories.PaperRepository;

@Service
public class PaperServiceImpl implements PaperService {
	
	@Autowired
	private PaperRepository paperRepo;
	
	@Autowired
	private PaperAttachementRepository paperAttachementRepo;
	
	@Value("${event.cutoffscore}")
	private float cuttoffscore;

	@Override
	public boolean addAuthorPaper(Paper paper) {
		if(paper == null) {
			throw new EditorAssistantException("Null Value Cannot be saved");
		}
		if(paper.getPaperId() == null) 
			paper.setPaperStatus(EditorAssistantConstants.PAPER_CREATED);
		Paper p = paperRepo.save(paper);
		
		savePaperAttachement2Db(paper, p.getPaperId());
		
		return true;
	}
	
	private void savePaperAttachement2Db(Paper paper, BigInteger paperId) {
		
		if(paper.getPaperAttachement() == null) {
			return;
		}
		PaperAttachement pa = new PaperAttachement();
		
		try {
			
			pa.setPaperId(paper.getPaperId());
			pa.setAttachement(paper.getPaperAttachement().getBytes());
		} catch (IOException e) {
			throw new EditorAssistantException("Error Occured while Saving the PDF!!");
		}
		paperAttachementRepo.save(pa);
	}

	@Override
	public boolean updateAuthorPaper(Paper paper) {
		Paper pa = paperRepo.findById(paper.getPaperId()).get();
		
		if(pa == null) {
			throw new EditorAssistantException("Paper  Not Found with Id: "+paper.getPaperId());
		}
		
		boolean isReviewersAssigened = verifyReviewersAssigned(paper);
		if(isReviewersAssigened) {
			paper.setPaperStatus(EditorAssistantConstants.REVIEWERS_ASSIGNED);
		}
		
		paperRepo.save(paper);
		return false;
	}

	@Override
	public List<Paper> getAllPaperByEvent(BigInteger eventId) {
		
		return paperRepo.findByEventId(eventId);
	}

	@Override
	public boolean deleteAuthorPaper(BigInteger paperId) {
		Paper paper = paperRepo.findById(paperId).get();
		
		if(paper == null) {
			throw new EditorAssistantException("Paper Not FOund with Id: "+paperId);
		}
		
		paperRepo.delete(paper);
		return true;
	}

	@Override
	public boolean updatePaperStatus(BigInteger paperId, String status) {
		
		Paper paper = paperRepo.findById(paperId).get();
		if(paper == null) {
			throw new EditorAssistantException("Paper Not found with Id: "+paperId);
		}
		String statusUpper = status != null ? status.toUpperCase().trim() : "";
		switch (statusUpper) {
		
		case EditorAssistantConstants.PAPER_SUBMITTED:
			 paper.setPaperStatus(EditorAssistantConstants.PAPER_SUBMITTED);
			break;

		case EditorAssistantConstants.ELIGIBLE_TO_PUBLISH:
			 paper.setPaperStatus(EditorAssistantConstants.ELIGIBLE_TO_PUBLISH);
			 break;
			
		case EditorAssistantConstants.PAPER_PUBLISHED:
			 paper.setPaperStatus(EditorAssistantConstants.PAPER_PUBLISHED);
			 break;
			
		default:
			throw new EditorAssistantException("Status Sent is Invalid!!");
			
		}
		
		paperRepo.save(paper);
		return true;
	}

	@Override
	public List<Paper> getAllPaperByAuthor(BigInteger authorId) {
		
		return paperRepo.findByAuthorId(authorId);
	}
	
	@Override
	public Paper getPaperById(BigInteger paperId) {
		Optional<Paper> paper = paperRepo.findById(paperId);
		
		if(paper.isPresent()) {
			return paper.get();
		} else {
			throw new EditorAssistantException("Paper Not Found");
		}
		
		
	}
	
	public byte[] getAtachement(BigInteger paperId ) {
		Optional<PaperAttachement> paperAttch = paperAttachementRepo.findById(paperId);
		
		if(paperAttch.isPresent()) {
			return paperAttch.get().getAttachement();
		} else {
			throw new EditorAssistantException("PaperAttachement Not Uploaded!!");
		}
	}

	@Override
	public List<Paper> getAllPapersByReviewerAndEvent(BigInteger reviewerId, BigInteger eventId) {
		List<Paper> reviewerPaperList = null;
		List<Paper> eventPaperList = getAllPaperByEvent(eventId);
		if(eventPaperList != null) {
			reviewerPaperList = eventPaperList.stream().filter(eventPaper -> eventPaper.getReviewer1().equals(reviewerId) 
				|| eventPaper.getReviewer2().equals(reviewerId)
				|| eventPaper.getReviewer3().equals(reviewerId)).collect(Collectors.toList());
		}
		return reviewerPaperList;
	}

	@Override
	public void updatePaperByField(BigInteger paperId, List<FieldDto> fieldDtoList) {
		Paper paper = getPaperById(paperId);
		try {
			
	fieldDtoList.stream().forEach(fieldDto -> {	
		
	String value = fieldDto.getFieldValue() != null ? fieldDto.getFieldValue().toString().trim() : null;
	
	 String fieldName = fieldDto.getFieldName().trim();
	 
		switch (fieldName) {
		case "reviewer1Score":
			paper.setReviewer1Score(Integer.parseInt(value));
			break;
        case "reviewer2Score":
			paper.setReviewer2Score(Integer.parseInt(value));
			break;
        case "reviewer3Score":
			paper.setReviewer3Score(Integer.parseInt(value));
			break;
        case "reviewer1Feedback":
			paper.setReviewer1Feedback(value);
			break;
        case "reviewer2Feedback":
			paper.setReviewer2Feedback(value);
			break;
        case "reviewer3Feedback":
			paper.setReviewer3Feedback(value);
			break;

		default:
			throw new EditorAssistantException("field [" + fieldName + "] not defined for update");
		}
	});
		
	} catch (EditorAssistantException e) {
		throw e;
	}catch (Exception e) {
		throw new EditorAssistantException("field [" + e.getMessage() + "] not defined for update");
	}
		if(verifyPaperReviewed(paper)) {
			paper.setPaperStatus(EditorAssistantConstants.PAPER_REVIEWED);
			paper.setAverageScore(calculateAverageScore(paper));
		}
		if(paper.getAverageScore() != null && paper.getAverageScore() >= cuttoffscore) {
			paper.setPaperStatus(EditorAssistantConstants.ELIGIBLE_TO_PUBLISH);
		}
		paperRepo.save(paper);
	}

	@Override
	public List<Paper> getAllPapersByStatusAndEvent(BigInteger eventId, String paperStatus) {
		List<Paper> paperListByEvent = getAllPaperByEvent(eventId);
		List<Paper> submittedPapers = new ArrayList<Paper>();
	    List<String> statusList =	Arrays.asList(EditorAssistantConstants.PAPER_CREATED,EditorAssistantConstants.PAPER_SUBMITTED, EditorAssistantConstants.REVIEWERS_ASSIGNED,
				EditorAssistantConstants.ELIGIBLE_TO_PUBLISH,EditorAssistantConstants.PAPER_REVIEWED, EditorAssistantConstants.PAPER_PUBLISHED);
		if (paperStatus.equalsIgnoreCase(EditorAssistantConstants.ELIGIBLE_TO_PUBLISH)) {
			submittedPapers = paperListByEvent.stream().filter(paper -> paper.getPaperStatus().equals(paperStatus))
					.collect(Collectors.toList());
			submittedPapers.addAll(paperListByEvent.stream().filter(paper -> paper.getPaperStatus().equals(EditorAssistantConstants.PAPER_PUBLISHED))
					.collect(Collectors.toList()));
			return submittedPapers;
		}
	    if(statusList.contains(paperStatus)) {
		submittedPapers = paperListByEvent.stream().filter(paper -> paper.getPaperStatus().equals(paperStatus))
				.collect(Collectors.toList());
		} else {
			throw new EditorAssistantException("invalid status!");
		}
		return submittedPapers;
	}
	
	private boolean verifyReviewersAssigned(Paper paper) {
		
		if(paper.getReviewer1() != null && paper.getReviewer2() != null && paper.getReviewer3() != null) {
		return true;
		}
		
		return false;
	}
	
	private boolean verifyPaperReviewed(Paper paper) {
		
		boolean isReviewersAssigend = verifyReviewersAssigned(paper);
		if(isReviewersAssigend && (paper.getReviewer1Score() != 0 && paper.getReviewer2Score() != 0) && paper.getReviewer3Score() != 0) {
			return true;
		}
		return false;
	}
	
	private float calculateAverageScore(Paper paper) {
		float averageScore = ((paper.getReviewer1Score() + paper.getReviewer2Score() + paper.getReviewer3Score()) / 3);
		
		return averageScore;
	}
  
}
