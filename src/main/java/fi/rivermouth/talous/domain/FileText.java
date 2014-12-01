package fi.rivermouth.talous.domain;

import javax.persistence.Entity;

import net.wimpi.telnetd.io.terminal.ansi;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
