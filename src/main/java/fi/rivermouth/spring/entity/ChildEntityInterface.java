package fi.rivermouth.spring.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ChildEntityInterface
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable> {
	
	@JsonIgnore
	public PARENT getParent();
	public void setParent(PARENT parent);
}
