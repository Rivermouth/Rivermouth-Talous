package fi.rivermouth.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Responsable {
	public String KIND = "entity";
	@JsonIgnore
	public String getKind();
}
