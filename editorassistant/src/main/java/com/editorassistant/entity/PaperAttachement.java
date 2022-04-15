package com.editorassistant.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table( name = "paper_attachement")
public class PaperAttachement implements Serializable {

	private static final long serialVersionUID = 8537115634658678857L;
	
	@Id
	@Column( name = "paper_id", unique = true, nullable = false, precision = 28, scale = 0)
	private BigInteger paperId;
	
	@Lob
	@Column( name = "attachment")
	private byte[] attachement;

	public PaperAttachement() {
		
	}
	
	

}
