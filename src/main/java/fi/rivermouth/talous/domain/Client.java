package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Client extends AbstractCompany<Long> implements ChildEntityInterface<User, Long> {
	
	@ManyToOne // owned by user
	@NotNull
	private User owner;
	
	@OneToMany // has projects
	private List<Project> projects;
	
	@OneToMany // has notes
	private List<Note<Client, Long>> notes;
	
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

	public List<Note<Client, Long>> getNotes() {
		return notes;
	}

	public void setNotes(List<Note<Client, Long>> notes) {
		this.notes = notes;
	}

	@Override
	public String getKind() {
		return "client";
	}

	@Override
	public User getParent() {
		return getOwner();
	}

	@Override
	public void setParent(User parent) {
		setOwner(parent);
	}
	
}
