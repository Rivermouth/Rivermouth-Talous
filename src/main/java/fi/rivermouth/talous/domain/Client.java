package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import fi.rivermouth.talous.model.Address;

@Entity
public class Client extends AbstractCompany<Long> {

	private Long parentId;
	
	@OneToMany // has projects
	private List<Project> projects;
	
	@OneToMany // has notes
	private List<ClientNote> notes;
	
	public Client() {
		super();
	}
	
	public Client(String name) {
		super(name);
	}
	
	public Client(String name, String vatNumber, Address address) {
		super(name, vatNumber, address);
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<ClientNote> getNotes() {
		return notes;
	}

	public void setNotes(List<ClientNote> notes) {
		this.notes = notes;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getKind() {
		return "client";
	}
	
}
