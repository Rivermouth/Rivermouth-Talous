package fi.rivermouth.talous.domain;

import javax.persistence.Entity;

@Entity
public class UserNote extends AbstractNote<User, Long> {
	
	public UserNote() {}
	
	public UserNote(String title) {
		super(title);
	}
	
	public UserNote(String title, String content) {
		super(title, content);
	}
	
}
