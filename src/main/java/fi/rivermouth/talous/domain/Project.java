package fi.rivermouth.talous.domain;

import javax.persistence.Entity;

import fi.rivermouth.spring.entity.Responsable;

@Entity
public class Project extends BaseEntity implements Responsable {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getKind() {
		return "project";
	}
	
}
