package fi.rivermouth.talous.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import fi.rivermouth.spring.entity.BaseEntity;

@Entity
public class File extends BaseEntity<Long> {
	
	private String title;
	private String mimeType;
	private Long size;
	private String collection;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;
	
	public File() {
	}
	
	public File(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public Long getSize() {
		return size;
	}
	
	public void setSize(Long size) {
		this.size = size;
	}
	
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	@Override
	public String getKind() {
		return "file";
	}
	
}
