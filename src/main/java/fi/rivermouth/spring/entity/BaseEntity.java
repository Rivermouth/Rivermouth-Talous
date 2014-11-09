package fi.rivermouth.spring.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public abstract class BaseEntity<ID extends Serializable> extends AbstractPersistable<ID> implements Responsable {
	
	public static final String path = "";

	@Override
	@JsonIgnore
	public boolean isNew() {
		return super.isNew();
	}
	
	@Override
	public void setId(ID id) {
		super.setId(id);
	}
	
}
