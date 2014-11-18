package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.entity.Responsable;

@Entity
public class Project extends BaseEntity<Long> implements Responsable {

	private String name;
	
	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL, fetch = FetchType.EAGER) // has notes
	private List<File> notes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<File> getNotes() {
		return notes;
	}

	public void setNotes(List<File> notes) {
		this.notes = notes;
	}

	@Override
	public String getKind() {
		return "project";
	}
	
}
