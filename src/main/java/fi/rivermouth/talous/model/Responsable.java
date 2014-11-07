package fi.rivermouth.talous.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Responsable {
	public String KIND = "entity";
	@JsonIgnore
	public String getKind();
}
