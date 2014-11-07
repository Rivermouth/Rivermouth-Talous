package fi.rivermouth.talous.domain;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fi.rivermouth.talous.model.Responsable;

@Entity
public abstract class BaseEntity extends AbstractPersistable<Long> implements Responsable {
	
	@Override
	@JsonIgnore
	public boolean isNew() {
		return super.isNew();
	}
	
	public void setId(Long id) {
		super.setId(id);
	}
	
}
