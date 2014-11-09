package fi.rivermouth.talous.domain;

import javax.persistence.Entity;

@Entity
public class ClientNote extends AbstractNote<Client, Long> {

	public ClientNote() {}
	
	public ClientNote(String title) {
		super(title);
	}
	
	public ClientNote(String title, String content) {
		super(title, content);
	}
	
}
