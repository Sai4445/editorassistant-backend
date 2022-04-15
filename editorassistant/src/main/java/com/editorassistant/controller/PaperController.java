package com.editorassistant.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.editorassistant.annotations.ExcludeCustomResponseProcessing;
import com.editorassistant.config.EditorAssistantServiceBase;
import com.editorassistant.dto.FieldDto;
import com.editorassistant.entity.Paper;
import com.editorassistant.service.PaperServiceImpl;



@RestController
@RequestMapping("/paper")
public class PaperController extends EditorAssistantServiceBase {
	
	@Autowired
	private PaperServiceImpl paperService;
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public boolean addAuthorPaper(@ModelAttribute Paper paper) {
		checkUser();
		return paperService.addAuthorPaper(paper);
	}
	
	
	@PutMapping("/updatePaper")
	public boolean updateAuthorPaper(@RequestBody Paper paper) {
		checkAdmin();
		return paperService.updateAuthorPaper(paper);
	}
	
	@GetMapping("/getAllPapersByEvent/{eventId}")
	public List<Paper> getAllPapersByEvent(@PathVariable BigInteger eventId) {
		checkUser();
		return paperService.getAllPaperByEvent(eventId);
	}
	
	@GetMapping("/getAllPapersByAuthor/{authorId}")
	public List<Paper> getAllPapersByAuthor(@PathVariable BigInteger authorId) {
		checkUser();
		return paperService.getAllPaperByAuthor(authorId);
		
	}
	
	@GetMapping("/getAllPapersByReviewerAndEvent/{reviewerId}/{eventId}")
	public List<Paper> getAllPapersByReviewerAndEvent(@PathVariable BigInteger reviewerId,
			                                             @PathVariable BigInteger eventId){
		checkUser();
		return paperService.getAllPapersByReviewerAndEvent(reviewerId, eventId);
	}
	
	@PutMapping("/upDatePaperFields/{paperId}")
	public boolean updatePaperFieldById(@PathVariable(name = "paperId", required = true) BigInteger paperId,
	        @RequestBody(required = true) List<FieldDto> fieldDataList ) {
		checkUser();
		paperService.updatePaperByField(paperId, fieldDataList);
		return true;
	}
	
	@DeleteMapping("/deletePaperById/{paperId}")
	public boolean deletePaperById(@PathVariable BigInteger paperId) {
		return paperService.deleteAuthorPaper(paperId);
	}
	
	@GetMapping("/{paperId}")
	public Paper getPaperById(@PathVariable BigInteger paperId) {
		checkUser();
		return paperService.getPaperById(paperId);
	}
	
	@GetMapping("/getAllPapersStatus/{eventId}")
	public List<Paper> getAllSubmitted(@PathVariable BigInteger eventId, @RequestParam String paperStatus) {
		checkAdmin();
		return paperService.getAllPapersByStatusAndEvent(eventId, paperStatus);
	}
	
	@ExcludeCustomResponseProcessing
    @GetMapping("/getPaperAttachement/{paperId}")
    public ResponseEntity<byte[]> getResume(@PathVariable BigInteger paperId) {
        checkUser();
        byte[] data = paperService.getAtachement(paperId);

        Paper paper = paperService.getPaperById(paperId);

        String downloadFileName = "PAPER-" + paperId + "-"
				+ paper.getAuthorId()/* .trim().replace(" ", ".").toUpperCase() */ + ".pdf";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment; filename=" + downloadFileName).build());

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
    
    @PutMapping("/changePaperStatus")
    public boolean changePaperStatus(@RequestParam BigInteger paperId, @RequestParam String status) {
       checkUser();
    	return paperService.updatePaperStatus(paperId, status);
    }

}
