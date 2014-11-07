package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Client extends AbstractCompany {
	
	@ManyToOne
	@NotNull
	private User owner;
	
	@OneToMany
	private List<Project> projects;
	
	@OneToMany
	private List<Note> notes;
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Project> getProject() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	@Override
	public String getKind() {
		return "client";
	}
	
}
