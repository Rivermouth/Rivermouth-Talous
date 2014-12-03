package fi.rivermouth.talous.domain;

import java.util.Date;

import javax.persistence.Entity;

import fi.rivermouth.spring.entity.Responsable;

@Entity
public class Project extends BaseEntity implements Responsable {

	private String name;
	private String description;
	private Date deadline;
	private boolean accomplished;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public boolean isAccomplished() {
		return accomplished;
	}

	public void setAccomplished(boolean accomplished) {
		this.accomplished = accomplished;
	}

	@Override
	public String getKind() {
		return "project";
	}
	
}
