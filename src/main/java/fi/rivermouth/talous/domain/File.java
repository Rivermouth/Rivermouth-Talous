package fi.rivermouth.talous.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class File extends AbstractAttachment {

	private String mimeType;
	private Long size;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;
	
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
	
	@Override
	public String getKind() {
		return "attachment:file";
	}
	
}
