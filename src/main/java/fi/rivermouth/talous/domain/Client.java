package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Validation;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;

import org.hibernate.annotations.Cascade;

import fi.rivermouth.talous.model.Address;

@Entity
public class Client extends AbstractCompany<Long> {

	@OneToMany(fetch = FetchType.LAZY) // has clients
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<Project> projects;

	@OneToMany(fetch = FetchType.LAZY) // has clients
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<File> notes;
	
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

	public List<File> getNotes() {
		return notes;
	}

	public void setNotes(List<File> notes) {
		this.notes = notes;
	}
	
	@Override
	public String getKind() {
		return "client";
	}
	
}
