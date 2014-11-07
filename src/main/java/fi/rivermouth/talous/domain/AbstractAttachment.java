package fi.rivermouth.talous.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import fi.rivermouth.talous.model.Responsable;

@Entity
public abstract class AbstractAttachment extends BaseEntity implements Responsable {
	
	private String title;
	@ManyToOne
	private BaseEntity attachedTo;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public BaseEntity getAttachedTo() {
		return attachedTo;
	}
	
	public void setAttachedTo(BaseEntity attachedTo) {
		this.attachedTo = attachedTo;
	}

	@Override
	public String getKind() {
		return "attachment";
	}
	
}
