package fi.rivermouth.talous.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fi.rivermouth.talous.model.Responsable;

@Entity
public abstract class BaseEntity<ID extends Serializable> extends AbstractPersistable<ID> implements Responsable {
	
	@Override
	@JsonIgnore
	public boolean isNew() {
		return super.isNew();
	}
	
	public void setId(ID id) {
		super.setId(id);
	}
	
}
