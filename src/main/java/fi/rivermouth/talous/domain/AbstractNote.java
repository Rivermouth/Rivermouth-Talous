package fi.rivermouth.talous.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ManyToAny;

import fi.rivermouth.spring.entity.BaseEntity;

@Entity
public abstract class AbstractNote<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable> 
extends AbstractAttachment<PARENT, PARENT_ID, Long> {

	private String content;
	
//	@OneToMany
//	private List<File> files;
	
	public AbstractNote() {
		super();
	}
	
	public AbstractNote(String title) {
		super(title);
	}
	
	public AbstractNote(String title, String content) {
		this(title);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public List<File> getFiles() {
//		return files;
//	}
//
//	public void setFiles(List<File> files) {
//		this.files = files;
//	}

	@Override
	public String getKind() {
		return "note:attachment";
	}
	
}
