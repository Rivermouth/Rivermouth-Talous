package fi.rivermouth.talous.domain;

import java.io.IOException;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class File extends BaseEntity {
	
	private Long owner;
	private Long attachedTo;
	
	private String name;
	private String mimeType;
	private Long size;
	private String collection;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;

	public File() {
	}
	
	public File(String name, byte[] content) {
		this.name = name;
		setContent(content);
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}
	
	public Long getAttachedTo() {
		return attachedTo;
	}

	public void setAttachedTo(Long attachedTo) {
		this.attachedTo = attachedTo;
	}
	
	public File(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	@JsonIgnore
	public Long getSize() {
		return size;
	}
	
	public void setSize(Long size) {
		this.size = size;
	}
	
	@JsonIgnore
	public byte[] getContent() {
		return content;
	}
	
	/**
	 * Set content of File and update its size
	 * @param content
	 */
	@JsonProperty
	public void setContent(byte[] content) {
		this.content = content;
		this.size = (long) content.length;
	}
	
	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}
	
	@JsonProperty
	public String getThumbnailUrl() {
		switch (mimeType.split("/")[0]) {
		case "image":
			return getDownloadUrl();
		default:
			return "https://cdn2.iconfinder.com/data/icons/windows-8-metro-style/256/file.png";
		}
	}
	
	@JsonProperty
	public String getDownloadUrl() {
		return "http://localhost:8080/files/" + getOwner() + "/" + getId();
	}

	@Override
	public String getKind() {
		return "file";
	}
	
}
