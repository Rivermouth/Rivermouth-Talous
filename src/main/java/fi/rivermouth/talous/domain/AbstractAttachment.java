package fi.rivermouth.talous.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import fi.rivermouth.talous.model.Responsable;

@Entity
public abstract class AbstractAttachment
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable, // Parent
ID extends Serializable> 
extends BaseEntity<ID> implements Responsable {
	
	private String title;
	@ManyToOne
	private PARENT attachedTo;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public PARENT getAttachedTo() {
		return attachedTo;
	}
	
	public void setAttachedTo(PARENT attachedTo) {
		this.attachedTo = attachedTo;
	}

	@Override
	public String getKind() {
		return "attachment";
	}
	
}
