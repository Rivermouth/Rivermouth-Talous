package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fi.rivermouth.talous.model.Responsable;

@Entity
public class Project extends BaseEntity<Long> implements Responsable {

	private String name;
	
	@ManyToOne // owned by client
	private Client client;
	
	@OneToMany // has notes
	private List<ProjectNote> notes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<ProjectNote> getNotes() {
		return notes;
	}

	public void setNotes(List<ProjectNote> notes) {
		this.notes = notes;
	}

	@Override
	public String getKind() {
		return "project";
	}
	
}
