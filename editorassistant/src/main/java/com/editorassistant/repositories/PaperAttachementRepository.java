package com.editorassistant.repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.editorassistant.entity.PaperAttachement;

public interface PaperAttachementRepository extends JpaRepository<PaperAttachement, BigInteger>{
	
	@Query("select pa.paperId from PaperAttachement pa")
	List<PaperAttachement> findPaperIdList();

}
