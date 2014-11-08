package fi.rivermouth.talous.domain;

import java.io.Serializable;

import javax.persistence.ManyToOne;

public interface AbstractAttachmentInterface<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable> {
	@ManyToOne
	public PARENT getAttachedTo();
	public void setAttachedTo(PARENT attachedTo);
}
