package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import fi.rivermouth.talous.model.Address;

@Entity
public class Client extends AbstractCompany {

	@OneToMany(fetch = FetchType.LAZY)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<Project> projects;

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
	
	@Override
	public String getKind() {
		return "client";
	}
	
}
