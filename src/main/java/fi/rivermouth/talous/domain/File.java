package fi.rivermouth.talous.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import org.springframework.stereotype.Component;

//@Entity // owned by note
@Component
public abstract class File {} /*extends AbstractAttachment<AbstractNote<File, Long>, Long, Long> {
	
	private AbstractNote<File, Long> attachedTo;

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

	@Override
	public AbstractNote<File, Long> getAttachedTo() {
		return attachedTo;
	}

	@Override
	public void setAttachedTo(AbstractNote<File, Long> attachedTo) {
		this.attachedTo = attachedTo;
	}
	
}
*/