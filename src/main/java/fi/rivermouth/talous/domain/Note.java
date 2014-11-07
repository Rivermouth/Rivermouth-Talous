package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Note extends AbstractAttachment {

	private String content;
	
	@OneToMany
	private List<File> files;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
	
	@Override
	public String getKind() {
		return "note:attachment";
	}
	
}
