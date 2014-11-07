package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import fi.rivermouth.talous.model.Responsable;

@Entity
public class Project extends BaseEntity implements Responsable {

	private String name;
	
	@ManyToOne
	private Client client;
	
	@OneToMany
	private List<Note> notes;

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

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	@Override
	public String getKind() {
		return "project";
	}
	
}
