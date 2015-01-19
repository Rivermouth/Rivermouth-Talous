package fi.rivermouth.talous.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonGetter;

@Entity
public class FileText extends File {
	
	public FileText(File file) {
		setAttachedTo(file.getAttachedTo());
		setCollection(file.getCollection());
		setContent(file.getContent());
		setId(file.getId());
		setMimeType(file.getMimeType());
		setName(file.getName());
		setOwner(file.getOwner());
	}

	@JsonGetter("content")
	public String getContentText() {
		return new String(getContent());
	}
	
}
