package fi.rivermouth.talous.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import fi.rivermouth.talous.model.Responsable;

@Entity
public abstract class AbstractAttachment
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable, // Parent
ID extends Serializable> 
extends BaseEntity<ID> implements Responsable, AbstractAttachmentInterface<PARENT, PARENT_ID> {
	
	private String title;
	
	public AbstractAttachment() {}
	
	public AbstractAttachment(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getKind() {
		return "attachment";
	}
	
}
