package fi.rivermouth.talous.domain;

import javax.persistence.Entity;

@Entity
public class ProjectNote extends AbstractNote<Project, Long> {

	public ProjectNote() {}
	
	public ProjectNote(String title) {
		super(title);
	}
	
	public ProjectNote(String title, String content) {
		super(title, content);
	}

}
